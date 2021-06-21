
//......................查询...........................
@Override
public List<InputInvoiceItemDto> getInvoiceItems(Long id) {
    List<InputInvoiceItem> items = inputInvoiceItemMapper.selectList(
            new QueryWrapper<InputInvoiceItem>()
                    .lambda()
                    .eq(InputInvoiceItem::getInvoiceId, id) //eq查询
    );
    List<InputInvoiceItemDto> itemDtos = modelMapper.map(items, new TypeToken<List<InputInvoiceItemDto>>(){}.getType());
    return itemDtos;
}

//发票查询
//获取发票，专票，当前税号，发票号码,认证状态不为空 筛选
LambdaQueryWrapper<InputInvoice> invoiceQueryWrapper = new LambdaQueryWrapper<>();
invoiceQueryWrapper.eq(InputInvoice::getBuyerTaxNumber, taxNo)
        .eq(InputInvoice::getInvoiceType, InputInvoiceTypeEnum.VAT_SPECIAL_INVOICE.value())
        .in(InputInvoice::getInvoiceNumber, invoiceNumberList)
        .in(InputInvoice::getInvoiceCode, invoiceCodeList)
        .isNotNull(InputInvoice::getVerifyStatus);

List<InputInvoice> invoices = inputInvoiceMapper.selectList(invoiceQueryWrapper);


//语法
//https://www.jianshu.com/p/07be9ccb3306

//distinct ,join as 数组
List<String> typeList = tpList.stream().filter(t -> t.getProductType() != null)
.map(t -> t.getProductType().toString()).distinct()
.collect(Collectors.toList());

String mapStr = String.join(",", typeList);


 //in 查询
 List<Long> invoiceIdList = dtoList.stream().map(t -> t.getInvoiceId()).collect(Collectors.toList());
 List<InputInvoice> invoiceList = inputInvoiceMapper.selectList(
         new QueryWrapper<InputInvoice>()
                 .lambda()
                 .in(InputInvoice::getId, invoiceIdList) //in查询



//......................更新...........................


//mybatis plus update 实践
String productTpe ="1";
for (InputInvoiceItem item : items) {
LambdaUpdateWrapper<InputInvoiceItem> updateWrapper = new LambdaUpdateWrapper<>();
updateWrapper.eq(InputInvoiceItem::getId, item.getId())
.set(InputInvoiceItem::getProductType, productTpe)
.set(InputInvoiceItem::getUpdateBy, authUserHelper.getCurrentUserId())
.set(InputInvoiceItem::getUpdateTime, new Date());
inputInvoiceItemMapper.update(null, updateWrapper);

//这儿也可以这样写： 但是如果系统自己实现了，可能就不行，因为他们会判断空值，有时是需要空的
InputInvoiceItem newItem =new InputInvoiceItem();
newItem.setProductType( productTpe);
newItem.setUpdateBy(authUserHelper.getCurrentUserId());
newItem.setId( item.getId());
newItem.setUpdatTime( new Date());
inputInvoiceItemMapper.updateById(newItem);

}

//发票更新实践 update(,wrapper)，这个主要用户批量更新
LambdaUpdateWrapper<InputInvoice> updateWrapper = new LambdaUpdateWrapper<>();
updateWrapper.eq(InputInvoice::getId, invoiceId);

InputInvoice updateInv = new InputInvoice();
updateInv.setVerifyStatus(certifiedStatus);
updateInv.setUpdateBy(authUserHelper.getCurrentUserId());
updateInv.setPeriod(deductiblePeriod);
updateInv.setUpdateTime(new Date());
inputInvoiceMapper.update(updateInv, updateWrapper);


//这儿也可以这样写(不用wrapper)，但是如果系统自己实现了，可能就不行，因为他们会判断空值，有时是需要空的
InputInvoice updateInv =new InputInvoice();
updateInv.setVerifyStatus(certifiedStatus);
updateInv.setUpdateBy(authUserHelper.getCurrentUserId());
updateInv.setPeriod(deductiblePeriod);
updateInv.setUpdateTime(new Date());
updateInv.setId(invoiceId);
inputInvoiceItemMapper.updateById(newItem);



//......................删除...........................


@Test
public void deleteDemo() {
    userMapper.deleteById(1L);
    Map<String, Object> map = new HashMap<>();
    map.put("id", 1L);
    userMapper.deleteByMap(map);
    
    //MP lambda 删除
    LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<User>().eq(User::getId, 1L);
    userMapper.delete(qw);
    //delete batch
    List<Long> ids = Arrays.asList(1L, 2L, 3L);
    userMapper.deleteBatchIds(ids);
}

//......................分页...........................


//接口 
  IPage<InputInvoiceDisplayDO> selectByCondition(IPage<InputInvoiceDisplayDO> dto, InvoiceConditionQueryDO condition);

  //实现
    // 分页查询
    PageHelper.startPage(pagingDto.getPageIndex(), pagingDto.getPageSize());
    final Page<InputInvoiceDisplayDO> queryDto = new Page<>(pagingDto.getPageIndex(), pagingDto.getPageSize());

    return inputInvoiceMapper.selectByCondition(queryDto, conditionQueryDO);


//自定义分页
public class DocInfoSearchDto extends DocInfoDto {
        private PagingDto pagingDto;

@RedisCache(name = "docSearchInfo")
public IPage<DocInfoDto> getDocInfo(DocInfoSearchDto search) {
    if (search.getPagingDto() == null) {
        search.setPagingDto(new PagingDto(1, 20));
    }
    search.setFlag(true);
    final Page<DocInfoDto> page =
            new Page<>(search.getPagingDto().getPageIndex(), search.getPagingDto().getPageSize());

    val searchDto = modelMapper.map(search, DocInfoDto.class);

    return docInfoMapper.selectDocInfo(page, searchDto);
}


public void TestPage(){
   //plus 分页
    LambdaQueryWrapper<InputInvoice> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.isNotNull(InputInvoice::getInvoiceNumber); //这儿如果用t->会报错

    //总页数+总记录数
    Page<InputInvoice> page = new Page<>(10, 20);
    //调用自定义sql
    IPage<InputInvoice> iPage = inputInvoiceMapper.selectPage(page, queryWrapper);
}
 



//**************解决批量插入问题************** */

//plus解决批量插入问题 但是底层是for循环单个插入
//https://blog.csdn.net/leisure_life/article/details/98976565
//分三步走
//1,使用方法，定义一个自己的接口，继承IService，泛型为被操作实体类
@Service
public  interface  iplusUserService extends IService<User> {

}

//2,定义一个实现类，实现上诉接口
@Service
public class plusUserService extends ServiceImpl<UserMapper, User> implements IPlusUserService {
}


//3,使用
@Resource
private IPlusUserService _plusUserService;

List<User> paramList = new ArrayList<>();
for (int i = 0; i < 10; i++) {
    User arr = new User();
    arr.setUsername("jack" + i);
    arr.setSex("M");
    arr.setAddress("chengdu");
    arr.setBirthday(new Date());
    paramList.add(arr);
}
Boolean b = _plusUserService.saveBatch(paramList);

//和单条插入的执行对比了一下，在1000条数据级别内，差别不大，批量操作的优势可能大数据环境下才能显现吧



//事务
//方法上加： @Transactional



@RedisCache(name = "xxx")
//设置xxx缓存，xxx名称全局不重复
@RedisCacheEvict(name = "xxx")
清楚xxx缓存，放在修改的地方

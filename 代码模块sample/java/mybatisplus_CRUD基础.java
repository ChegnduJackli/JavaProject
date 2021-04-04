
//*****************插入 insert********************* */

User user3 = new User();
user3.setUsername("大神2");
user3.setSex("M");
user3.setAddress("xxx");
userMapper.insert(user3);


//*****************更新 update********************* */

// a,根据id更新
User user3 = new User();
user3.setId(3);
user3.setUsername("jack update");
userMapper.updateById(user3);


//b,条件构造器更新 更新name='jack'的 name='jack22',常用条件更新
LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
updateWrapper.eq(User::name, "jack");
User user2 = new User();
user2.setUsername("jack22");
userMapper.update(user2,updateWrapper);

//c, 当表中字段很多,只更新少量字段的值（建议使用）
//可以使用updateWrapper的set方法
LambdaUpdateWrapper<InputInvoiceItem> updateWrapper = new LambdaUpdateWrapper<>();
updateWrapper.eq(InputInvoiceItem::getId, item.getId())
.set(InputInvoiceItem::getProductType, productTpe)
inputInvoiceItemMapper.update(null, updateWrapper);


//只更新一个属性，把名字为rhb的用户年龄更新为18，其他属性不变(不推荐用字段，推荐用lambda)
UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
updateWrapper.eq("name","rhb").set("age", 18);
userMapper.update(null, updateWrapper);

//这个跟上面一样的效果： lambda构造器 (比较推荐)
LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
lambdaUpdateWrapper.eq(User::getName, "rhb").set(User::getAge, 18);
Integer rows = userMapper.update(null, lambdaUpdateWrapper);




//*****************删除 delete********************* */

//a,delete by id 
int id =4789;
int count = userMapper.deleteById(id);

//b,delete by batch id
int count = userMapper.deleteBatchIds(Arrays.asList(1, 2, 4, 5)));


//c,条件删除
LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
wrapper.eq(User::getUsername,"jack19")
        .eq(User::getSex,"M");
userMapper.delete(wrapper);

//还有一种map方法
HashMap<String,Object> map= new HashMap<>()
map.put("name","jack19");
map.put("sex","M");
userMapper.deleteByMap(map);



//*****************查询 query********************* */

//a,通过id查询
List<InputInvoiceItem> items = inputInvoiceItemMapper.selectList(
new QueryWrapper<InputInvoiceItem>()
.lambda()
.eq(InputInvoiceItem::getInvoiceId, id) //eq查询


//b,多个条件查询
//获取发票，专票，当前税号，发票号码,认证状态不为空 筛选
LambdaQueryWrapper<InputInvoice> invoiceQueryWrapper = new LambdaQueryWrapper<>();
invoiceQueryWrapper.eq(InputInvoice::getBuyerTaxNumber, taxNo)
        .eq(InputInvoice::getInvoiceType, InputInvoiceTypeEnum.VAT_SPECIAL_INVOICE.value())
        .in(InputInvoice::getInvoiceNumber, invoiceNumberList)
        .in(InputInvoice::getInvoiceCode, invoiceCodeList)
        .isNotNull(InputInvoice::getVerifyStatus);

List<InputInvoice> invoices = inputInvoiceMapper.selectList(invoiceQueryWrapper);


//c,根据条件，动态查询
LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
String name ="";
String sex="M";
if(StringUtils.isNotEmpty(name)){
    queryWrapper.like(User::getUsername,name);
}
if(StringUtils.isNotEmpty(sex)){
    queryWrapper.eq(User::getSex,sex);
}
List<User> users = userMapper.selectList(queryWrapper);


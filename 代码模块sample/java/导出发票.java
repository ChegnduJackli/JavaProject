
/**
 * 进项发票导出excel,
 *
 * @param {String} fileName 导出文件名称
 * @param {JSON} data {condition: {...}, pagination: {...}}
 */
export function inputInvoiceExport(fileName,data){
        return requestBlob({
        url: `/v1/invoiceManage/inputInvoiceExport/${fileName}`,
        method:'post',
        data
        })
        }

@ApiOperation(value = "inputInvoiceExport", notes = "进项发票导出")
@PostMapping(value = "inputInvoiceExport/{fileName}")
public void inputInvoiceExport(
        HttpServletResponse response,
@PathVariable(required = false) String fileName,
@RequestBody InvoiceConditionQueryDO condition){
        logger.debug("enter InvoiceManageController inputInvoiceExport");
        Assert.notNull(condition,"parameter is empty");

        try{
        invoiceManageService.inputInvoiceExport(response,fileName,condition);
        }catch(IOException e){
        e.printStackTrace();
        }
        }


@Override
public void inputInvoiceExport(HttpServletResponse response,String fileName,InvoiceConditionQueryDO condition)throws IOException{
        List<InputInvoiceDisplayDO> inputInvoiceDisplayList=new ArrayList<>();

        Integer pageSize=5000;
        Integer currentPage=1;
        Page<InputInvoiceDisplayDO> pageQuery=new Page<>(currentPage,pageSize);

        IPage<InputInvoiceDisplayDO> pageRes=inputInvoiceMapper.selectByCondition(pageQuery,condition);
        if(CollectionUtils.isNotEmpty(pageRes.getRecords())){
        inputInvoiceDisplayList.addAll(pageRes.getRecords());
        }

        if(pageSize.equals(pageRes.getRecords().stream().count())){
        currentPage++;
        pageQuery=new Page<>(currentPage,pageSize);
        pageRes=inputInvoiceMapper.selectByCondition(pageQuery,condition);
        if(CollectionUtils.isNotEmpty(pageRes.getRecords())){
        inputInvoiceDisplayList.addAll(pageRes.getRecords());
        }
        }

        List<InputInvoiceExcelDTO> data=inputInvoiceDisplayList.stream().map(e->{
        InputInvoiceExcelDTO v=new InputInvoiceExcelDTO();
        beanUtil.copyProperties(e,v);
        return v;
        }).collect(Collectors.toList());

        try{
        EasyExcelUtil.writeWithTemplateWeb(response,fileName,data);
        }catch(Exception e){
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ApiResultVO result=ApiResultVO.fail("DownloadFailure");
        response.getWriter().println(JSON.toJSONString(result));
        }
        }
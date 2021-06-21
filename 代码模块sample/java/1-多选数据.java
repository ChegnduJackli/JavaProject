@Override
public List<InputInvoiceResultDTO> inputInvoiceDisable(List<Long> invoiceIdList){
        List<InputInvoiceResultDTO> invoiceResultList=new ArrayList<>();

        InputInvoiceExample example=new InputInvoiceExample();
        example.createCriteria().andIdIn(invoiceIdList);
        List<InputInvoice> inputInvoiceList=inputInvoiceMapper.selectByExample(example);

//*******************更新*************************//

   // 更改发票状态
   InputInvoice inputInvoiceToUpdate = new InputInvoice();
   inputInvoiceToUpdate.setId(inputInvoice.getId());
   inputInvoiceToUpdate.setStatus(InputInvoiceStatusEnum.Refunded.value());
   auditHelper.auditUpdate(inputInvoiceToUpdate);
   inputInvoiceMapper.updateByPrimaryKeySelective(inputInvoiceToUpdate);



   //*******************插入*************************//
InputInvoiceAddition inputInvoiceAdditionToInsert = new InputInvoiceAddition();
inputInvoiceAdditionToInsert.setId(idGenerator.nextId());
inputInvoiceAdditionToInsert.setInvoiceId(inputInvoice.getId());
inputInvoiceAdditionToInsert.setType(InputInvoiceAdditionTypeEnum.Refund.value());
inputInvoiceAdditionToInsert.setReason(invoiceRefund.getRefundReason());
inputInvoiceAdditionToInsert.setRemark(invoiceRefund.getRefundRemark());
auditHelper.auditCreate(inputInvoiceAdditionToInsert);
inputInvoiceAdditionMapper.insertSelective(inputInvoiceAdditionToInsert);
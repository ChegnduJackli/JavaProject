@Override
public List<InputInvoiceResultDTO> inputInvoiceDisable(List<Long> invoiceIdList){
        List<InputInvoiceResultDTO> invoiceResultList=new ArrayList<>();

        InputInvoiceExample example=new InputInvoiceExample();
        example.createCriteria().andIdIn(invoiceIdList);
        List<InputInvoice> inputInvoiceList=inputInvoiceMapper.selectByExample(example);

        InputInvoice disabledInvoice=new InputInvoice();
        for(InputInvoice inputInvoice:inputInvoiceList){
        if(InputInvoiceStatusEnum.Certified.value().equals(inputInvoice.getStatus())){
        // 已勾选认证的发票不可失效处理
        invoiceResultList.add(new InputInvoiceResultDTO(inputInvoice.getInvoiceCode(),inputInvoice.getInvoiceNumber(),inputInvoice.getStatus(),"CannotDisable"));
        }else{
        // 对于其它状态的发票简单认定可失效，后续可按需改动
        disabledInvoice.setId(inputInvoice.getId());
        disabledInvoice.setStatus(InputInvoiceStatusEnum.Deleted.value());
        inputInvoiceMapper.updateByPrimaryKeySelective(disabledInvoice);
        invoiceResultList.add(new InputInvoiceResultDTO(inputInvoice.getInvoiceCode(),inputInvoice.getInvoiceNumber(),disabledInvoice.getStatus(),"Success"));
        }
        }

        return invoiceResultList;
        }
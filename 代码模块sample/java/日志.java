 OperationLogParaDto tempCommonLogParams = new OperationLogParaDto();
tempCommonLogParams.setModule(OperationModule.Organization.value());

/*
关于初始和更新状态，
新增，填在更新里（setUpdateState）
修改，初始更新都有
删除，填在初始里（setOriginalState）
*/

tempCommonLogParams.setComment("");
tempCommonLogParams.setAction(OperationAction.New.value());
tempCommonLogParams.setOriginalState("");
tempCommonLogParams.setUpdateState(SeriliazeObj(organizationShareholder));
// 前台翻译key
tempCommonLogParams.setOperationContent(LogMessage.LogContentKey.GudongInfo);
// {机构名称} - {股东名称}
tempCommonLogParams.setOperationObject(logContent);
operationLogService.addOperationLogNew(tempCommonLogParams);


// 单独为董事加一条
OperationLogParaDto log2 = beanUtil.copyProperties(tempCommonLogParams, new OperationLogParaDto());
log2.setModule(OperationModule.Shareholder.value());
operationLogService.addOperationLogNew(log2);

// file log
OperationLogParaDto logFile = new OperationLogParaDto();
logFile.setOriginalState(SeriliazeObj(item));
logFile.setModule(OperationModule.Shareholder.value());
// 前台翻译key
logFile.setOperationContent(LogMessage.LogContentKey.GudongFile);
// {机构名称} - {股东名称}
logFile.setOperationObject(logContent);
logFile.setAction(OperationAction.Delete.value());
logFile.setComment("");
operationLogService.addOperationLogNew(logFile);


OperationLogParaDto logFile2 = beanUtil.copyProperties(logFile, new OperationLogParaDto());
logFile2.setModule(OperationModule.Organization.value());
operationLogService.addOperationLogNew(logFile2);


val logContent = organizationHK.getName() + LogMessage.logSeperator + calendarEvent.getTaskName()
	  + LogMessage.logSeperator + f.format(calendarEvent.getTaskDate());
	  

SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd ");
tempCommonLogParams.setOperationObject(
org.getName() + "-" + event.getTaskName() + "-" + f.format(event.getTaskDate()));




LogInputApiData apiData = new LogInputApiData();
apiData.setType(InvoiceApiDataTypeEnum.InvoiceVerify.getCode());
apiData.setIsSuccess(false);
apiData.setMessage("发票号码：" + invoice.getInvoiceNumber() + "超过当日验真次数");
insertInvoiceApiData(apiData, invoice, null);




      // add log
      String fromStatus = InputInvoiceStatusEnum.key(invoice.getStatus());
      String toUpdateStatus = InputInvoiceStatusEnum.key(status);

	  //复制一份
	  InputInvoice invoiceCopy = beanUtil.copyProperties(invoice, new InputInvoice());

      LogInputApiData apiData = new LogInputApiData();
      apiData.setType(InvoiceApiDataTypeEnum.InvoiceModify.getCode());
      apiData.setIsSuccess(true);
      apiData.setMessage("更新发票状态：从"+ Translation.toTrans(fromStatus)+ "改为" + Translation.toTrans(toUpdateStatus));
      apiData.setOriginalState(SeriliazeObj(invoiceCopy));
      apiData.setUpdateState(SeriliazeObj(entity));
      insertInvoiceApiData(apiData, invoice, null);

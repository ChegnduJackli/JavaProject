
//更新项目状态的某个字段
ProjectStatusExample example = new ProjectStatusExample();
        example.createCriteria().andOrgIdEqualTo(declareParameterDto.getOrgId())
        .andPeriodEqualTo(declareParameterDto.getPeriod())
                .andServiceTypeIdEqualTo(declareParameterDto.getLargeStatusId())
                .andStatusEqualTo(ProjectCardEnum.ProjectStatus.DECLARATION.getCode());

ProjectStatus projectStatus = projectStatuses.get(0);
projectStatus.setUpdateTime(new Date());
projectStatusMapper.updateByExample(projectStatus, example);//只修改更改时间 ==>>批量更新


//insert 
ProjectStatus projectStatus = new ProjectStatus();
statusId = idGenerator.nextId();
projectStatus.setTenantId(this.authUserHelper.getCurrentTenantId());
projectStatus.setId(statusId);
projectStatus.setCreateTime(new Date());
projectStatus.setStatus(ProjectCardEnum.ProjectStatus.DECLARATION.getCode());
projectStatus.setServiceTypeId(declareParameterDto.getLargeStatusId());
projectStatus.setOrgId(declareParameterDto.getOrgId());
projectStatus.setPeriod(declareParameterDto.getPeriod());
projectStatus.setCreateBy(authUserHelper.getCurrentUserId());
projectStatusMapper.insertSelective(projectStatus); //插入不为空的字段



//
@Update("update tenant_client set is_active = #{status} where tenant_id =  #{tenantId};")
@SqlParser(filter = true)
@MycatSchema
void updateTenantStatus(@Param(value = "status") Integer status, @Param(value = "tenantId") String tenantId);


tenantMapper.updateTenantStatus(cts.getStatus(), cts.getTenantId());



@Override
public List<InputInvoiceResultDTO> inputInvoiceDisable(List<Long> invoiceIdList){
        List<InputInvoiceResultDTO> invoiceResultList=new ArrayList<>();

        InputInvoiceExample example=new InputInvoiceExample();
        example.createCriteria().andIdIn(invoiceIdList);
        List<InputInvoice> inputInvoiceList=inputInvoiceMapper.selectByExample(example);

//*******************更新*************************//

   // 更改发票状态
   //先查询出来，再更新
   InputInvoice inputInvoice =inputInvoiceMapper.selectByPrimaryKey(invoiceRefund.getId(), authUserHelper.getCurrentTenantId());
   InputInvoice inputInvoiceToUpdate = new InputInvoice();
   inputInvoiceToUpdate.setId(inputInvoice.getId());
   inputInvoiceToUpdate.setStatus(InputInvoiceStatusEnum.Refunded.value());
   auditHelper.auditUpdate(inputInvoiceToUpdate);
   inputInvoiceMapper.updateByPrimaryKeySelective(inputInvoiceToUpdate);


   //打印sql语句，部分字段更新，更新不为NULL的，
   UPDATE input_invoice SET status = 13, update_by = 376012987674939392, update_time = '2021-05-08 14:56:05.56'
 WHERE tenant_id = 'PwC_376008330517417985' AND id = 382132741624066052 AND tenant_id = 'PwC_376008330517417985';
       


   //*******************插入*************************//

InputInvoiceAddition inputInvoiceAdditionToInsert = new InputInvoiceAddition();
inputInvoiceAdditionToInsert.setId(idGenerator.nextId());
inputInvoiceAdditionToInsert.setInvoiceId(inputInvoice.getId());
inputInvoiceAdditionToInsert.setType(InputInvoiceAdditionTypeEnum.Refund.value());
inputInvoiceAdditionToInsert.setReason(invoiceRefund.getRefundReason());
inputInvoiceAdditionToInsert.setRemark(invoiceRefund.getRefundRemark());
auditHelper.auditCreate(inputInvoiceAdditionToInsert);
inputInvoiceAdditionMapper.insertSelective(inputInvoiceAdditionToInsert);


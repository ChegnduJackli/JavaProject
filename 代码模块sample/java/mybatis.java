
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
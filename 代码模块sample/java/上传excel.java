

// 追加和覆盖导入
<el-row>
<el-col:span="24">
<el-form-item:label="$t('invoice.column.uploadMethod')">
<el-radio v-model="entityModal.isCoverImport"
        :label="false">{{$t('mgt.taxCodeMapping.appendImport')}}</el-radio>

<el-radio v-model="entityModal.isCoverImport"
        :label="true">{{$t('mgt.taxCodeMapping.coverImport')}}</el-radio>
</el-form-item>
</el-col>
</el-row>

//排序sortBy key
        this.gudongDataSource=this.orgInfo.shareholders.sortBy('id');
        this.dongshiDataSource=this.orgInfo.directors.sortBy("id");


        addAttachment(file){
        this.formData.append("file",file.file);
        },

        uploadFile(done){
        this.formData=new FormData();
        this.$refs["ruleForm"].validate((valid)=>{
        if(!valid){
        done();
        return false;
        }

        if(this.$refs.upload.$children[0].fileList.length>0){
        this.$refs.upload.submit();
        }else{
        this.$mb.warning(this.$t("mgt.orgManage.PleaseSelectFileFirst"));
        done();
        return;
        }
        });
        }

        this.fd.append('fileType','category')
        this.fd.append('orgId',this.customerForm.orgId)
        this.fd.append('isCoverImport',this.customerForm.isCoverImport)
        this.fd.append("dtoStr",JSON.stringify(data)); //如果多个数据

//后台接受string,然后反序列化为对象
@PostMapping("addDocInfo")
public Object addDocInfo(@RequestParam List<MultipartFile> files,@RequestParam String dtoStr){
        DocInfoDto dto;
        try{
        dto=JSON.parseObject(dtoStr,DocInfoDto.class);
        if(dto.getTypeCode()==null){
        dto.setTypeCode("");
        }
        }
        }
        // _this.docService
        //   .addDocInfo(_this.fd)

        // 多文件上传
        uploadExcelCustomerFile(this.fd).then(rsp=>{
        const resp=rsp
        })


@ApiOperation(value = "uploadExcelCustomerFile", notes = "上传Excel发票文件")
@RequestMapping(value = "uploadExcelCustomerFile", method = RequestMethod.POST)
public @ResponseBody
      OperationResultVO uploadExcelInvoiceFile(@RequestParam MultipartFile file,@RequestParam Long orgId,@RequestParam boolean isCoverImport){
        logger.debug("enter CustomerMaintenanceController uploadExcelCustomerFile");
        OperationResultVO resultDto=new OperationResultVO();
        try{
        if(file==null||file.getSize()<=0){
        return new OperationResultVO(false,ErrorMessage.NoFile);
        }
        logger.debug("file name: "+file.getOriginalFilename());
        resultDto=customerMaintenanceService.uploadExcelCustomerFile(file,orgId,isCoverImport);

        }catch(RuntimeException e){
        e.printStackTrace();
        logger.error(e.getMessage());
        resultDto.setResult(false);
        resultDto.setMessage(e.getMessage());
        }
        return resultDto;
        }
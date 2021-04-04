<template>
  <el-dialog :title="modalTitle"
    :visible="isShowModal"
    :before-close="handleClose"
    :close-on-click-modal="false"
    @open="beforeOpen"
    custom-class="file-upload-download-dialog "
    :append-to-body="true">

    <el-form label-position="left"
      :model="entityModal"
      :rules="getRules"
      ref="ruleForm"
      label-width="100px">

      <el-row v-if=" optionConfig.isShowOrg">
        <el-col :span="24">
          <el-form-item :label="$t('invoice.outputConfig.belongOrganization')"
            prop="orgId">
            <el-select v-model="entityModal.orgId"
              clearable
              filterable
              :placeholder="$t('common.pleaseSelect')">
              <el-option v-for="item in orgList"
                :key="item.id"
                :label="item.name"
                :value="item.id" />
            </el-select>
          </el-form-item>
        </el-col>

      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item :label="$t('invoice.outputConfig.chooseFile')"
            style="margin-bottom: 0;">
            <el-upload ref="upload"
              drag
              action=""
              :limit="1"
              accept=".xlsx,.xls"
              :http-request="addAttachment"
              :file-list="fileList"
              :auto-upload="false"
              width="100%">
              <i class="el-icon-upload" />
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- <el-row>
        <el-col :span="24">
          <el-col :span="24">
            <el-form-item :label="$t('common.downloadtemplate')">
              <ck-btn type="primary"
                @click="downloadTemplateCheck"> {{ $t('common.downloadtemplate') }}</ck-btn>
            </el-form-item>
          </el-col>
        </el-col>
      </el-row> -->
      <el-row>
        <el-col :span="24">
          <el-form-item :label="$t('invoice.column.uploadMethod')">
            <el-radio v-model="entityModal.isCoverImport"
              :label="false">{{ $t('mgt.taxCodeMapping.appendImport') }}</el-radio>

            <el-radio v-model="entityModal.isCoverImport"
              :label="true">{{ $t('mgt.taxCodeMapping.coverImport') }}</el-radio>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <span slot="footer"
      class="dialog-footer">

      <ck-btn-edit-link style="float:left;"
        @click="downloadTemplateCheck">
        <i class="iconfont-o  icon-download-light-outline"></i> {{ $t('common.downloadtemplate') }}
      </ck-btn-edit-link>

      <ck-btn @click="handleClose">
        {{ $t('invoice.button.close') }}
      </ck-btn>
      <ck-btn type="primary"
        :auto-loading="true"
        @click="uploadFile($event)">
        {{ $t('invoice.button.upload') }}
      </ck-btn>
    </span>
  </el-dialog>
</template>

<style lang="scss" scoped>
/deep/ .file-upload-download-dialog {
  width: 40%;
  //height: 60vh;
}
.el-input,
.el-select,
.el-input-number,
.el-date-editor.el-input {
  width: 100%;
}
/deep/ .el-upload {
  width: 100%;
}
/deep/ .el-upload-dragger {
  width: 100%;
}

//控制子层的样式
</style>

<script>

import {
  uploadExcelCommonFile,
  downloadTemplateCommon as downloadTemplate
} from '@/api/input';
import { saveAs } from "file-saver";
import {
  gv
} from '@/utils/gv';

let _instance = null;
let _className = gv.uploadClassName;

let defaultConfig = function () {
  return {
    modalTitle: '上传文件',
    isShowOrg: true,
    isShowDownloadTemplate: true,
    className: '',
    importMode: true,
    overWriteMsg: '覆盖上传会更新现有数据，确定继续？',
    downloadTemplateName: '', //下载文件文件
    type: 'default', //上传下载的type，后台根据区分
    //如果还有其他对象需要传递到后台
    uploadData: {
      test: 'test',
    }
  }
};

//存储类对象
let _animalClass = {
  // Animal,
  // Dog,
  // uploadExcelToVerify,
  // uploadExcelToRevokeVerify
};



class Animal {
  constructor() {
    this.config = defaultConfig();
  }

  //哪个类实例，就是哪个类的config
  //比如 uploadExcelToVerify 实例，就是它的新对象
  toString () {
    // ...
    console.log('this.config', this.config)
  }
  test () {
    console.log('Animal.test', this.config);
  }

  /**上传到服务器 ,如果自己的不一样，请在自己类里实现
   * @formData,文件对象
   * @uploadObj ,页面的数据，比如机构id，是否覆盖上传
   */
  uploadToServer (formData, uploadObj) {

    let mergeObj = { ...this.config.uploadData, ...uploadObj };

    //需要自己的数据传递到后台，合并对象 uploadObj
    formData.append("dtoStr", JSON.stringify(mergeObj)); //如果多个数据

    return new Promise((resolve, reject) => {
      uploadExcelCommonFile(formData).then((res) => {
        console.log('res', res);
        resolve(res);
      }).catch((e) => {
        console.error(e);
        reject(e);
      });
    });
  }

  //同一个方法提供下载方法，根据参数type区分，如果需要自己定义新api,
  //在自己的类里覆盖该方法
  downloadTemplate () {
    let downloadTemplateName = this.config.downloadTemplateName;
    let downloadType = this.config.type;
    let obj = {
      type: downloadType,
      downloadTemplateName
    };

    let dtoStr = JSON.stringify(obj);
    //console.log('downloadTemplate', downloadTemplateName);
    downloadTemplate(dtoStr).then((res) => {
      if (!!res) {
        var blob = new Blob([res.data], {
          type: res.headers["content-type"],
        });
        saveAs(blob, downloadTemplateName);
      }
    }).catch((e) => {
      console.error(e);
      this.$mb.error(this.$t('common.downloadFail'));
    });
  }

}



//【发票勾选】： 上传到后台，用于发票筛选做勾选 或者撤销
_animalClass[_className.uploadVerifyInvoice] = class extends Animal {
  constructor() {
    super();
    this.configOptions = {
      isShowOrg: true,
      modalTitle: '上传勾选清单',
      downloadTemplateName: '上传勾选清单模板.xlsx',
      type: 'invoiceverify', //上传下载的type，后台根据区分
    };
    this.config = { ...this.config, ...this.configOptions }
  }
}

// //【撤销发票勾选】： 上传到后台，用于发票筛选做撤销勾选
// _animalClass[_className.UploadRevokeInvoice] = class extends Animal {
//   constructor() {
//     super();
//     this.configOptions = {
//       isShowOrg: true,
//       modalTitle: '上传撤销勾选清单',
//       downloadTemplateName: '上传撤销勾选清单模板.xlsx',
//       type: 'invoiceRevokeverify', //上传下载的type，后台根据区分
//     };
//     this.config = { ...this.config, ...this.configOptions }
//   }
// }


class factoryClass {
  constructor(className) {
    if (!_animalClass[className]) {
      console.error('class does not exist');
      return;
    }
    return new _animalClass[className];
  }
}


import { getAllActiveOrgList } from '@/api/organization'

export default {
  name: "fileDownloadUpload",
  //props: ['title'],
  //每个 prop 都有指定的值类型
  props: {
    isShowModal: {
      type: Boolean,
      default: false,
    },
    className: {
      type: String,
      require: true,
    },
    // option: {
    //   type: Object,
    //   default: defaultConfig()
    // },
  },

  data () {
    return {
      modalTitle: '',
      entityModal: {
        orgId: null,
        isCoverImport: false,
      },
      fileList: [],
      orgList: [],
      formData: new FormData(),
      optionConfig: defaultConfig(),
    };
  },
  components: {

  },
  computed: {
    getRules () {
      return {
        orgId: [
          {
            required: this.optionConfig.isShowOrg,
            message: this.$t('common.pleaseSelect') + this.$t("invoice.outputConfig.belongOrganization"),
            trigger: "change",
          }
        ]
      }
    },
  },
  methods: {
    getAllActiveOrgList () {
      getAllActiveOrgList().then(res => {
        if (res && res.code === 0) {
          this.orgList = res.data.sortBy('name');
          // console.log(res.data)
        }
      }).finally(() => {

      })
    },
    //下载模板
    downloadTemplateCheck () {
      _instance && _instance.downloadTemplate();
    },

    initData () {
      this.optionConfig = { ...this.optionConfig, ..._instance.config };
      //this.optionConfig = Object.assign({}, this.optionConfig, _instance.config);
      this.modalTitle = this.optionConfig.modalTitle;
      if (this.optionConfig.isShowOrg) {
        this.getAllActiveOrgList();
      }
    },

    createInstance () {
      _instance = new factoryClass(this.className);
      // _instance.toString();
      //_instance.test();
      this.initData();
    },

    beforeOpen () {
      // this.optionConfig = defaultConfig();
      // console.log('this.optionConfig', this.optionConfig);
      console.log('className', this.className);
      this.createInstance();
      setTimeout(() => {
        this.$refs && this.$refs["ruleForm"] && this.$refs["ruleForm"].resetFields();
      }, 10);
    },
    handleClose () {
      this.resetData();
      this.$emit('update:isShowModal', false);
    },
    resetData () {
      Object.assign(this.$data, this.$options.data()); //data重置
    },
    addAttachment (file) {
      this.formData.append("file", file.file);
    },
    //上传文件到服务器
    uploadFile (done) {
      this.formData = new FormData();
      this.$refs["ruleForm"].validate((valid) => {
        if (!valid) {
          done();
          return false;
        }

        if (this.$refs.upload.$children[0].fileList.length > 0) {
          this.$refs.upload.submit();
        } else {
          this.$mb.warning(this.$t("mgt.orgManage.PleaseSelectFileFirst"));
          done();
          return;
        }

        let updateObj = {
          orgId: this.optionConfig.isShowOrg ? this.entityModal.orgId : '-1',
          uploadType: this.optionConfig.type,
          isCoverImport: this.entityModal.isCoverImport
        };

        //由于参数，可能有多个，不定死，通过string传递到后台
        // this.formData.append('orgId', this.optionConfig.isShowOrg ?
        //   this.entityModal.orgId : '-1');

        // //后台根据次字段走不同的解析方法
        // this.formData.append('uploadType', this.optionConfig.type);
        // this.formData.append('isCoverImport', this.entityModal.isCoverImport);

        _instance && _instance.uploadToServer(this.formData, updateObj).then((res) => {
          if (res) {
            this.$mb.success(this.$t('common.uploadSuccess'));
            this.triggerParentEvent();
            this.handleClose();
          }
        }).catch((e) => {
          this.$mb.error(this.$t('common.uploadFail'))
        }).finally(() => {
          done();
        });
      });
    },
    //触发父层事件
    triggerParentEvent () {
      console.log('this', this);
      let _events = this._events;
      _events['callBack'] && this.$emit('callBack');
    }
  },
};
</script>

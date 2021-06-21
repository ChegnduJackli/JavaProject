//忽略加载的进度条

/**
 * 进项发票查验
 * @param {Array} data 发票 Id 数组
 */
export function inputInvoiceVerify (data) {
  return request({
    url: `/v1/invoiceManage/inputInvoiceVerify/`,
    method: 'post',
    data,
    ignoreBar: true,//不要显示进度条
  })
}


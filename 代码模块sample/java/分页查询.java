List<InputInvoiceDisplayDO> inputInvoiceDisplayList = new ArrayList<>();

Integer pageSize = 5000;
Integer currentPage = 1;
Page<InputInvoiceDisplayDO> pageQuery = new Page<>(currentPage, pageSize);

IPage<InputInvoiceDisplayDO> pageRes =
    inputInvoiceMapper.selectByCondition(pageQuery, condition);
if (CollectionUtils.isNotEmpty(pageRes.getRecords())) {
  inputInvoiceDisplayList.addAll(pageRes.getRecords());
}

while (pageSize.intValue() == (pageRes.getRecords().stream().count())) 
  currentPage++;
  pageQuery = new Page<>(currentPage, pageSize);
  pageRes = inputInvoiceMapper.selectByCondition(pageQuery, condition);
  if (CollectionUtils.isNotEmpty(pageRes.getRecords())) {
    inputInvoiceDisplayList.addAll(pageRes.getRecords());
  }
}


<el-footer>
<el-pagination style="float:right;margin-right:20px"
  layout="total, sizes, prev, pager, next, jumper"
  :page-sizes="
pageOption.pageSizeArray"
  :total="pageOption.totalCount"
  :page-size="pageOption.pageSize"
  :current-page="pageOption.pageIndex"
  @size-change="handleSizeChange"
  @current-change="handleCurrentChange" />
</el-footer>



<ck-pagination :page-option="pageOption"
@refresh="loadData"></ck-pagination>

data()return {
    pageOption: this.$_.cloneDeep(this.$gv.pagingOptions),
}



@ApiOperation(value = "获取翻译展示数据")
@PostMapping(value = "/api/v1/trans/getTranslationDisplayList")
public @ResponseBody
PagingResultDto<TranslationListDto> getTranslationDisplayList(
        @RequestBody TranslationSearchReq search) {
    PagingResultDto<TranslationListDto> ret = new PagingResultDto<>();
    IPage<TranslationListDto> data = translationService.getTranslation(search);
    ret.setList(data.getRecords());
    PagingDto pageDto = new PagingDto();
    pageDto.setPageIndex((int)data.getCurrent());
    pageDto.setPageSize((int)data.getSize());
    pageDto.setTotalCount(data.getTotal());
    ret.setPageInfo(pageDto);
    return ret;
}  

public IPage<TranslationListDto> getTranslation(TranslationSearchReq search){

        final Page<TranslationListDto> page = new Page<>(search.getPagingDto().getPageIndex(), search.getPagingDto().getPageSize());
        return translationValueMapper.selectBySearch(page, search.getLanFlag(), search.getKeyFlag(), search.getLanguage(), search.getKey(), search.getValue());
}
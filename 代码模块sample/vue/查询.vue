 <template>

  <ck-search-box @refreshData="search"
    style="width:300px"
    :filterText.sync="searchText"
    :is-real-time="true">
  </ck-search-box>
</template>


<script>


//dx的查询
search() {
  let val = this.searchText;
  this.dxOptions.instance.searchByText(val);
},
onContentReady(e) {
  if (e && e.component) {
    this.dxOptions.instance = e.component;
  }
},
</script>
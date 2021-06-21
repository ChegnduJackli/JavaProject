
//内部处理
var memoryService = {
    populateInfoTable: function (data, selectedItems) {
        var InvoiceCodeColumn = translate('InvoiceFPDM'); //发票代码
        var InvoiceNumberColumn = translate('InvoiceFPHM');//发票号码
        var IssueStatusColumn = '状态'; //状态

        var columnList = [InvoiceCodeColumn, InvoiceNumberColumn, IssueStatusColumn];

        var infoList = [];

        selectedItems.forEach(function (row) {
            var returnResult = _.find(data, function (item) {
                return item.id === row.id;
            });
            var line = {};
            line[InvoiceCodeColumn] = row.invoiceCode;
            line[InvoiceNumberColumn] = row.invoiceNumber;

            line[IssueStatusColumn] = returnResult.resultMsg;
            infoList.push(line);
        });

        var infoTable = ackLib.populateTable(columnList, infoList);
        return infoTable;
    };
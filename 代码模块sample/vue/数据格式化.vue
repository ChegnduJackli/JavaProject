// 金额 格式化
<DxColumn data-field="amount"
data-type="number"
:caption="$t('invoice.output.amount')"
cell-template="amount" />
<template #amount="{ data }">
<div>

<span>{{ $formatter.formatCurrency(0,0,data.data.amount) }}</span>
</div>
</template>


//税率
<DxColumn data-field="taxRate"
data-type="number"
cell-template="taxRate"
:caption="$t('invoice.output.taxRate')" />
<template #taxRate="{ data }">
<div>
<span>{{ $formatter.formatTaxRate(data.data.taxRate) }}</span>
</div>
</template>

千分位 1,000
{{entityModal.payment_deductibleNum | toThousandFilter}}

千分位+2位小数 1,000.00
{{entityModal.payment_deductibleTax | toThousandFixedFilter}}

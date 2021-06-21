try {
    inputInvoice.setCertDate(DateUtils.parseDateStrictly(dateStr, "yyyy-MM-dd"));
  } catch (ParseException e) {
    // logger.warn("Cert Date parse failed");
  }


  String startDate = "20201001";
  String endDate = "20201030";
  if (dto != null) {
    Date startDate1 = dto.getCurrentPeriodCheckedOpenDate();
    Date endDate1 = dto.getCurrentPeriodCheckedCloseDate();

    SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
    startDate = f.format(startDate1);
    endDate = f.format(endDate1);
  }
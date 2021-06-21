    @Test
    public void test() {
        List<Message> messageList = getMessageList();
        //1, List过滤
        List<Message> list2 = messageList.stream().filter(s->s.getMsg().equals("a")).collect(Collectors.toList());
        list2.stream().forEach(item -> System.out.println(item));

        //2,distinct去重
        List<String> distinctMsg = messageList.stream().map(Message::getMsg).distinct().collect(Collectors.toList());
        distinctMsg.forEach(System.out::println);

        //3排序
        //3.1 单个字段排序，根据id升序
       // messageList.sort((a,b)->a.getId().compareTo(b.getId()));
        //根据Id升序排序(简写)
        //messageList.sort(Comparator.comparing(Message::getId));

        //根据Id降序排序(简写)
       // messageList.sort(Comparator.comparing(Message::getId).reversed());



        //3.2多个字段排序
        //根据多条件组合排序,先根据msg(升序),再根据id(升序)
        //messageList.sort(Comparator.comparing(Message:: getMsg).thenComparing(Message::getId));

        //根据多条件组合排序,先根据msg(升序),再根据id(降序)
       // messageList.sort(Comparator.comparing(Message:: getMsg).thenComparing(Comparator.comparing(Message::getId).reversed()));

        //根据多条件组合排序,先根据msg(降序),再根据id(降序)
        //messageList.sort(Comparator.comparing(Message:: getMsg).thenComparing(Message::getId).reversed());

        //根据多条件组合排序,先根据msg(降序),再根据id(升序)
        //messageList.sort(Comparator.comparing(Message:: getMsg).reversed().thenComparing(Message::getId));

        //System.out.println("===排序后如下===");
        //messageList.stream().forEach(item -> System.out.println(item));

        //4, findFirst
        Optional<Message> msg1 =  messageList.stream().filter(s->s.getMsg().equals("e")).findFirst();
        Optional<Message> msg2 =  messageList.stream().filter(s->s.getMsg().equals("e")).findAny();
        if(msg1.isPresent()){
            Message s1 = msg1.get();
            System.out.println(s1 );
        }
        if(msg2.isPresent()){
            Message s2 = msg2.get();
            System.out.println(s2 );
        }
    }
    private List<Message> getMessageList(){
        List<Message> list = new ArrayList<>();
        list.add(setValue(2L,"a"));
        list.add(setValue(1L,"b"));
        list.add(setValue(8L,"c"));
        list.add(setValue(4L,"d"));
        list.add(setValue(6L,"e"));
        list.add(setValue(3L,"e"));
        list.add(setValue(5L,"a"));
        return list;
    }

    private Message setValue(Long id, String msg){
        var message = new Message();
        message.setId(id);
        message.setMsg(msg);
        message.setSendTime(new Date());
        return  message;
    }








//*****************************Map操作********************** */
//学习网站： https://www.cnblogs.com/lzq198754/p/5780165.html

@Test
public void testMap() {
    List<Message> messageList = getMessageList();
    //Map 接口中键和值一一映射. 可以通过键来获取值。 key不能重复，否则报错
    Map<Long,Message> map = messageList.stream().collect(Collectors.toMap(Message::getId, Function.identity()));
    // System.out.println(map );

    // //通过key,获取元素
    // Message msg1 =map.get(1L);
    // System.out.println(msg1 );

    //keySet遍历  No:1
    //    for(Long key : map.keySet())
    //    {
    //        System.out.println(key + " ：" + map.get(key));
    //    }
    //    //entrySet遍历 迭代器 No:2
    //    for (Map.Entry<Long, Message> entry : map.entrySet()) {
    //        System.out.println(entry.getKey() + " ：" + entry.getValue());
    //    }
    //keyset 迭代器 No:3
    //    Iterator<Long> iterator = map.keySet().iterator();
    //    while (iterator.hasNext()) {
    //        Long key = iterator.next();
    //        System.out.println(key + "　：" + map.get(key));
    //    }
    //entrySet迭代器（性能最好，大数据推荐使用） No:4
    Iterator<Map.Entry<Long, Message>> iterator = map.entrySet().iterator();
    while (iterator.hasNext()) {
        Map.Entry<Long ,Message> entry = iterator.next();
        System.out.println(entry.getKey() + "　：" + entry.getValue());
    }
    //总结 性能
    //entrySet迭代器 >    keyset 迭代器 > For entrySet遍历 > For keySet遍历
    //增强for循环使用方便，但性能较差，不适合处理超大量级的数据。
    //迭代器的遍历速度要比增强for循环快很多，是增强for循环的2倍左右。
    //使用entrySet遍历的速度要比keySet快很多，是keySet的1.5倍左右。

}

///////////////////普通操作
//判断集合是否有值
if (CollectionUtils.isNotEmpty(pageRes.getRecords())) {
    inputInvoiceDisplayList.addAll(pageRes.getRecords());
  }

  
  //检测是否为null 可以使用CollectionUtils.isEmpty()
if (CollectionUtils.isEmpty(collection)){
    System.out.println("collection is null.");
}



//求和
  BigDecimal sumInvoiceAmount = invoices.stream().map(InputInvoice::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
  BigDecimal sumAccountDetailAmount = accountDetails.stream().map(InputInvoiceAccountDetail::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

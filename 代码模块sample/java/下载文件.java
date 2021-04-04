public void download(String path,HttpServletResponse response)throws Exception{
        File filePath=new File(path);
        OutputStream out=null;
        try(InputStream inputStream=new FileInputStream(filePath)){

        out=response.getOutputStream();
        response.setContentType("application/octet-stream");

        byte[]buff=new byte[1024];
        int len;
        while((len=inputStream.read(buff))!=-1){
        out.write(buff,0,len);
        }
        out.flush();
        }catch(Exception ex){
        throw ex;
        }finally{
        if(filePath.exists()){
        filePath.delete();
        }
        }
        }

//文件的压缩和下载
//FileUploadService.compress(fileList, zipPath, false);
//FileUploadService.download(zipPath, response);
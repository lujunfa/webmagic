package org.patric.webmagic.demon2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.*;
import java.util.Map;

/**
 * @Author: lujunfa  2018/12/17 16:27
 *将数据持久层写在pipeline
 */

/**
 * @Component("JobInfoDaoPipeline")
 * public class JobInfoDaoPipeline implements PageModelPipeline<LieTouJobInfo> {
 *
 *     @Resource
 *     private JobInfoDAO jobInfoDAO;
 *
 *     @Override
 *     public void process(LieTouJobInfo lieTouJobInfo, Task task) {
 *         //调用MyBatis DAO保存结果
 *         jobInfoDAO.add(lieTouJobInfo);
 *     }
 * }
 */
public class MyDemonPipeLine implements Pipeline {
    Logger logger = LoggerFactory.getLogger(MyDemonPipeLine.class);
    @Override
    public void process(ResultItems resultItems, Task task) {
        FileWriter fileWriter = null;
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            try {
                fileWriter  = new FileWriter(new File("C:\\Users\\13574\\Desktop\\mysql.txt"));
                fileWriter.write(entry.getKey() + ":\t" + entry.getValue());
                fileWriter.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
               if(fileWriter !=null){
                   try {
                       fileWriter.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
            }
        }
    }
}

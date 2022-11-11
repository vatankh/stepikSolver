import com.beust.ah.A;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");
        WebDriver driver = new ChromeDriver();
        driver.get("https://stepik.org/catalog?auth=login");
        TimeUnit.SECONDS.sleep(4);
        driver.findElement(By.id("id_login_email")).sendKeys("username");
        driver.findElement(By.id("id_login_password")).sendKeys("password");
        driver.findElement(By.id("login_form")).submit();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("started looking for learn");
        driver.get("https://stepik.org/learn");
        System.out.println("in learn");
        TimeUnit.SECONDS.sleep(5);
        driver.findElement(By.className("lfcc__continue-btn")).click();
        System.out.println("submited");
        TimeUnit.SECONDS.sleep(6);
        WebElement next =driver.findElement(By.className("lesson__footer-nav-buttons"));
        WebElement quiz = null;
        while (next!= null){
            TimeUnit.SECONDS.sleep(1);
            try {
                quiz = driver.findElement(By.className("attempt"));
                solve(driver);
//                while (quiz != null){
//                    TimeUnit.SECONDS.sleep(3);
//                    List<WebElement> answersEl= driver.findElements(By.className("s-radio"));
//                    List<Answer> answers =new ArrayList<>();
//                    answersEl.forEach(ans ->{
//                        Answer  answer = new Answer(ans.getText(),ans);
//                        answers.add(answer);
//                    });
//                    for (int i=0;i<answers.size();i++){
//                        if (!answers.get(i).answered){
//                            answers.get(i).webElement.click();
//                            driver.findElement(By.className("submit-submission")).click();
//                            answers.get(i).answered=true;
//                            TimeUnit.SECONDS.sleep(4);
//                            try {
//                                WebElement e =  driver.findElement(By.xpath("//*[@class='again-btn success']"));
//                                e.click();
//                                TimeUnit.SECONDS.sleep(2);
//                                answersEl= driver.findElements(By.className("s-radio"));
//                                for (WebElement webElement : answersEl) {
//                                    String text = webElement.getText();
//                                    for (Answer answer : answers) {
//                                        if (answer.name.equals(text)) {
//                                            answer.webElement = webElement;
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                            catch (NoSuchElementException noSuchElementException){
//                                try {
//                                    WebElement element =  driver.findElement(By.xpath("//*[@class='ember-view attempt__wrapper_next-link button success']"));
//                                    element.click();
//                                    TimeUnit.SECONDS.sleep(3);
//                                    quiz=driver.findElement(By.className("attempt"));
//                                    break;
//                                } catch (NoSuchElementException noSuchElementException2){
//                                    break;
//                                }
//                            }
//                        }
//                    }
//
//                }
            }catch (NoSuchElementException noSuchElementException){
                next.click();
                next=driver.findElement(By.className("lesson__footer-nav-buttons"));
            }
        }

        System.out.println("next = null");
    }
    public static void solve(WebDriver driver) throws InterruptedException {
        System.out.println("solve again");
        TimeUnit.SECONDS.sleep(5);
        try {
            List<WebElement> answersEl= driver.findElements(By.className("s-radio"));
            if (answersEl.size()==0){
                throw new NoSuchElementException("because 0");
            }
            List<Answer> answers =new ArrayList<>();
            answersEl.forEach(ans ->{
                Answer  answer = new Answer(ans.getText(),ans);
                answers.add(answer);
            });
            for (int i=0;i<answers.size();i++){
                System.out.println("i= "+i);
                if (!answers.get(i).answered){
                    TimeUnit.SECONDS.sleep(2);

                    answers.get(i).webElement.click();
                    TimeUnit.SECONDS.sleep(2);

                    try {
                        driver.findElement(By.className("submit-submission")).click();

                    } catch (NoSuchElementException noSuchElementException){
                        System.out.println("okat here");
                    }
                    answers.get(i).answered=true;
                    TimeUnit.SECONDS.sleep(4);
                    try {
                        WebElement e =  driver.findElement(By.xpath("//*[@class='again-btn success']"));
                        e.click();
                        TimeUnit.SECONDS.sleep(3);
                        update(answers,driver);
                    }
                    catch (NoSuchElementException noSuchElementException){
                        System.out.println("fuck");
                        try {
                            WebElement element =  driver.findElement(By.xpath("//*[@class='ember-view attempt__wrapper_next-link button success']"));
                            element.click();
                            TimeUnit.SECONDS.sleep(3);
                            break;
                        } catch (NoSuchElementException noSuchElementException2){
                            System.out.println("iw ill click next");
                            driver.findElement(By.className("lesson__footer-nav-buttons")).click();
                            break;
                        }
                    }
                }else {
                    System.out.println("i -" +i);
                }
            }
            TimeUnit.SECONDS.sleep(2);
            System.out.println("im here");
            driver.findElement(By.className("lesson__footer-nav-buttons")).click();
            TimeUnit.SECONDS.sleep(2);


        }
        catch (NoSuchElementException noSuchElementException){
            System.out.println("-----------------");
            List<WebElement> answersEl= driver.findElements(By.className("s-checkbox"));
            List<Answer> answers =new ArrayList<>();
            answersEl.forEach(ans ->{
                Answer  answer = new Answer(ans.getText(),ans);
                answers.add(answer);
            });
            boolean exit=false;
            for (int i=0 ;i < answers.size();i++){
                if (exit){
                    break;
                }
                Answer answer = answers.get(i);

                answer.webElement.click();
                System.out.println("basic answer ="+answer.name);
                TimeUnit.SECONDS.sleep(1);
                driver.findElement(By.className("submit-submission")).click();
                try {
                    TimeUnit.SECONDS.sleep(5);
                    WebElement e =  driver.findElement(By.xpath("//*[@class='again-btn success']"));
                    e.click();
                    TimeUnit.SECONDS.sleep(3);
                    update(answers,driver);
                    for (int j=i+1;j< answers.size();j++){
                        answer.webElement.click();
                        TimeUnit.SECONDS.sleep(2);
                        Answer answer1=answers.get(j);
                        System.out.println("2level answer =" +answer1.name);
                        answer1.webElement.click();
                        TimeUnit.SECONDS.sleep(3);
                        driver.findElement(By.className("submit-submission")).click();
                        try {
                            TimeUnit.SECONDS.sleep(3);
                            e =  driver.findElement(By.xpath("//*[@class='again-btn success']"));
                             e.click();
                             TimeUnit.SECONDS.sleep(3);
                             update(answers,driver);
                             for (int k =j+1;k<answers.size();k++){
                                     Answer answer2 =answers.get(k);
                                     answer.webElement.click();
                                 TimeUnit.SECONDS.sleep(1);
                                 answer1.webElement.click();
                                 TimeUnit.SECONDS.sleep(1);
                                 answer2.webElement.click();
                                 System.out.println("3dlevel answer "+answer2);
                                 TimeUnit.SECONDS.sleep(3);
                                 driver.findElement(By.className("submit-submission")).click();
                                     try {
                                         TimeUnit.SECONDS.sleep(3);
                                         e =  driver.findElement(By.xpath("//*[@class='again-btn success']"));
                                         e.click();
                                         TimeUnit.SECONDS.sleep(3);
                                         update(answers,driver);
                                     } catch (NoSuchElementException suchElementException){
                                         System.out.println("69");
                                         TimeUnit.SECONDS.sleep(3);
                                         driver.findElement(By.className("lesson__footer-nav-buttons")).click();
                                         exit=true;
                                         break;
                                     }

                             }

                        }
                        catch (NoSuchElementException noSuchElementException1) {//return answer
                            TimeUnit.SECONDS.sleep(3);
                            driver.findElement(By.className("lesson__footer-nav-buttons")).click();
                            exit=true;
                            break;
                        }

                    }
                } catch (Exception noSuchElementException1){
                    //return answer
                    System.out.println("exited 5");
                    TimeUnit.SECONDS.sleep(1);
                    driver.findElement(By.className("lesson__footer-nav-buttons")).click();
                    exit=true;
                    break;
                }

            }
        }


    }
    static void update(List<Answer> answers,WebDriver driver){
        List<WebElement> answersEl= driver.findElements(By.className("s-checkbox"));
        for (WebElement webElement : answersEl) {
            String text = webElement.getText();
            for (Answer answer : answers) {
                if (answer.name.equals(text)) {
                    answer.webElement = webElement;
                    break;
                }
            }
        }

    }
    static class Answer{
        String name;
        boolean answered;

        WebElement webElement;
        Answer(String name, WebElement webElement){
            this.name=name;
            this.webElement=webElement;
            answered=false;
        }
        void check(){
            answered=true;
        }
    }
}

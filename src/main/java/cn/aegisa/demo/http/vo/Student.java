package cn.aegisa.demo.http.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 2019/1/7 22:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable, PostEntity {
    private String name;
    private Integer age;
}

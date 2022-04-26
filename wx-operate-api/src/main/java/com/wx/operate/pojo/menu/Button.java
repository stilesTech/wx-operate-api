package com.wx.operate.pojo.menu;


import lombok.*;

/**
 * @Description: 按钮的基类（每个按钮对象都有一个共同的name属性，
 * 因此需要定义一个按钮对象的基类，所有按钮对象都需要继承该类）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Button {

    private String name;

}
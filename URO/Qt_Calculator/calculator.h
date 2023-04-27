#ifndef CONVERTER_H
#define CONVERTER_H

#include <QWidget>
#include <iostream>
#include <string>

class QLineEdit;
class QSpinBox;
class QRadioButton;
class QPushButton;
class QGridLayout;
class QVBoxLayout;
class QHBoxLayout;
class QGroupBox;

class Calculator : public QWidget
{
    Q_OBJECT

    QGridLayout *layout;
    QVBoxLayout *panel;
    QLineEdit  *display;
    QPushButton *but1, *but2, *but3, *but4, *but5, *but6, *but7, *but8, *but9, *but0;
    QPushButton *addBut, *subBut, *mulBut, *divBut, *dotBut, *equalsBut, *acBut, *pmBut, *pctBut;

    double lastNum;
    char oper;
    bool eqClicked;

public:
    Calculator(QWidget *parent = 0);
    ~Calculator();

private:
    void insNumeral(char c);
    void setLastNum(double num);
    void setOperand(char op);

private slots:
    void clickButton0();
    void clickButton1();
    void clickButton2();
    void clickButton3();
    void clickButton4();
    void clickButton5();
    void clickButton6();
    void clickButton7();
    void clickButton8();
    void clickButton9();
    void clickAddBut();
    void clickSubBut();
    void clickMulBut();
    void clickDivBut();
    void clickOperand();
    void clickDotBut();
    void clickButtonEquals();
    void clickButtonAC();
    void clickButtonPM();
    void clickButtonPct();

};

#endif // CONVERTER_H

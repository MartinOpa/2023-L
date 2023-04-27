#include <QLabel>
#include <QLineEdit>
#include <QTableView>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QString>
#include <QButtonGroup>
#include <QRadioButton>
#include <QSpinBox>
#include <QGroupBox>
#include <QPushButton>
#include <QMessageBox>

#include "calculator.h"

#define MIN_BUT_WIDTH 30
#define MIN_BUT_HEIGHT 30
#define MAX_BUT_WIDTH 50
#define MAX_BUT_HEIGHT 50
#define NORMAL_FONT_SIZE 8
#define BIG_FONT_SIZE 18

Calculator::Calculator(QWidget *parent)
    : QWidget(parent)
{

    layout = new QGridLayout;

    panel = new QVBoxLayout;
    display = new QLineEdit("0",this);
    display->setReadOnly(true);
    display->setAlignment( Qt::AlignRight);
    panel->addWidget(display);
    layout->addLayout(panel, 0, 0, 1, 4);

    but1 = new QPushButton("1",this);
    but2 = new QPushButton("2",this);
    but3 = new QPushButton("3",this);
    but4 = new QPushButton("4",this);
    but5 = new QPushButton("5",this);
    but6 = new QPushButton("6",this);
    but7 = new QPushButton("7",this);
    but8 = new QPushButton("8",this);
    but9 = new QPushButton("9",this);
    but0 = new QPushButton("0",this);

    but1->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    but2->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    but3->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    but4->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    but5->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    but6->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    but7->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    but8->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    but9->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    but0->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);

    addBut = new QPushButton("+",this);
    subBut = new QPushButton("-",this);
    mulBut = new QPushButton("*",this);
    divBut = new QPushButton("/",this);
    dotBut = new QPushButton(".",this);
    equalsBut = new QPushButton("=",this);
    acBut = new QPushButton("AC",this);
    pmBut = new QPushButton("+/-",this);
    pctBut = new QPushButton("%",this);

    addBut->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    subBut->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    mulBut->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    divBut->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    dotBut->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    equalsBut->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    acBut->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    pmBut->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);
    pctBut->setMinimumSize(MIN_BUT_WIDTH, MIN_BUT_HEIGHT);

    connect(but0,SIGNAL(clicked()),this,SLOT(clickButton0()));
    connect(but1,SIGNAL(clicked()),this,SLOT(clickButton1()));
    connect(but2,SIGNAL(clicked()),this,SLOT(clickButton2()));
    connect(but3,SIGNAL(clicked()),this,SLOT(clickButton3()));
    connect(but4,SIGNAL(clicked()),this,SLOT(clickButton4()));
    connect(but5,SIGNAL(clicked()),this,SLOT(clickButton5()));
    connect(but6,SIGNAL(clicked()),this,SLOT(clickButton6()));
    connect(but7,SIGNAL(clicked()),this,SLOT(clickButton7()));
    connect(but8,SIGNAL(clicked()),this,SLOT(clickButton8()));
    connect(but9,SIGNAL(clicked()),this,SLOT(clickButton9()));
    connect(addBut,SIGNAL(clicked()),this,SLOT(clickAddBut()));
    connect(subBut,SIGNAL(clicked()),this,SLOT(clickSubBut()));
    connect(mulBut,SIGNAL(clicked()),this,SLOT(clickMulBut()));
    connect(divBut,SIGNAL(clicked()),this,SLOT(clickDivBut()));
    connect(dotBut,SIGNAL(clicked()),this,SLOT(clickDotBut()));
    connect(equalsBut,SIGNAL(clicked()),this,SLOT(clickButtonEquals()));
    connect(acBut,SIGNAL(clicked()),this,SLOT(clickButtonAC()));
    connect(pmBut,SIGNAL(clicked()),this,SLOT(clickButtonPM()));
    connect(pctBut,SIGNAL(clicked()),this,SLOT(clickButtonPct()));

    layout->addWidget(but1, 5, 0);
    layout->addWidget(but2, 5, 1);
    layout->addWidget(but3, 5, 2);
    layout->addWidget(but4, 4, 0);
    layout->addWidget(but5, 4, 1);
    layout->addWidget(but6, 4, 2);
    layout->addWidget(but7, 3, 0);
    layout->addWidget(but8, 3, 1);
    layout->addWidget(but9, 3, 2);
    layout->addWidget(but0, 6, 0, 1, 2);

    layout->addWidget(addBut, 5, 3);
    layout->addWidget(subBut, 4, 3);
    layout->addWidget(mulBut, 3, 3);
    layout->addWidget(divBut, 2, 3);
    layout->addWidget(dotBut, 6, 2);
    layout->addWidget(equalsBut, 6, 3);
    layout->addWidget(acBut, 2, 0);
    layout->addWidget(pmBut, 2, 1);
    layout->addWidget(pctBut, 2, 2);

    setLayout( layout );
}

Calculator::~Calculator()
{

}

void Calculator::insNumeral(char c) {
        QString txt = display->text();

        if (c == '.' && txt.contains('.')) {
            return;
        }

        if (txt == "0" && c != '.') {
            txt = "";
        }

        txt.append(c);
        display->setText(txt);
}

void Calculator::clickButton0() {
        insNumeral('0');
}

void Calculator::clickButton1() {
        insNumeral('1');
}

void Calculator::clickButton2() {
        insNumeral('2');
}

void Calculator::clickButton3() {
        insNumeral('3');
}

void Calculator::clickButton4() {
        insNumeral('4');
}

void Calculator::clickButton5() {
        insNumeral('5');
}

void Calculator::clickButton6() {
        insNumeral('6');
}

void Calculator::clickButton7() {
        insNumeral('7');
}

void Calculator::clickButton8() {
        insNumeral('8');
}

void Calculator::clickButton9() {
        insNumeral('9');
}

void Calculator::clickAddBut() {
        setOperand('+');
        clickOperand();
}

void Calculator::clickSubBut() {
        setOperand('-');
        clickOperand();
}

void Calculator::clickMulBut() {
        setOperand('*');
        clickOperand();
}

void Calculator::clickDivBut() {
        setOperand('/');
        clickOperand();
}

void Calculator::clickOperand() {
        if (!eqClicked) clickButtonEquals();

        double num = std::stod(display->text().toStdString());
        setLastNum(num);
        display->setText("0");

        eqClicked = false;
}

void Calculator::clickDotBut() {
        insNumeral('.');
}

void Calculator::clickButtonEquals() {
        if (lastNum == 0) {
            return;
        }

        eqClicked = true;

        std::string txt = display->text().toStdString();
        double res = 0;
        double current = std::stod(txt);

        if (oper == '+') {
            res = lastNum + current;
        } else
        if (oper == '-') {
            res = lastNum - current;
        } else
        if (oper == '*') {
            res = lastNum * current;
        } else
        if (oper == '/') {
            res = lastNum / current;
        } else {
            res = current;
        }

        std::string tempRes = std::to_string(res);
        tempRes.erase ( tempRes.find_last_not_of('0') + 1, std::string::npos );
        tempRes.erase ( tempRes.find_last_not_of('.') + 1, std::string::npos );
        QString resTxt = QString::fromStdString(tempRes);
        display->setText(resTxt);
        setLastNum(res);
        lastNum = res;
}

void Calculator::setLastNum(double num) {
        lastNum = num;
}

void Calculator::setOperand(char op) {
        oper = op;
}

void Calculator::clickButtonAC() {
        setOperand(' ');
        setLastNum(0);
        display->setText("0");
}

void Calculator::clickButtonPM() {
        std::string txt = display->text().toStdString();
        double res = std::stod(txt) * -1;
        QString resTxt = QString::fromStdString(std::to_string(res));
        display->setText(resTxt);
}

void Calculator::clickButtonPct() {
        std::string txt = display->text().toStdString();
        double res = std::stod(txt) * 0.01;
        QString resTxt = QString::fromStdString(std::to_string(res));
        display->setText(resTxt);
}


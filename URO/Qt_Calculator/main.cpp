#include <QtWidgets/QApplication>
#include <QString>

#include "calculator.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    Calculator w;
    w.setWindowTitle( QString( "OPA0023_Calculator") );
    w.show();
    return a.exec();
}

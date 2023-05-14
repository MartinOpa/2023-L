#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QStandardItem>
#include <QStandardItemModel>
#include <QPixmap>
#include <QComboBox>
#include <QDebug>

#include <vector>

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();
    void update();

    struct PackageInternational {
        std::string firstName1;
        std::string lastName1;
        std::string street1;
        std::string city1;
        int postalCode1;
        int phone1;
        std::string email1;

        std::string firstName2;
        std::string lastName2;
        std::string street2;
        std::string city2;
        int postalCode2;
        int phone2;
        std::string email2;

        int weight;
        double price;
        bool express;
        std::string country;
        bool tax;
        bool customs;
    };

    QStandardItemModel *model;
    std::vector<PackageInternational> data;

private slots:
    void on_tableView_clicked(const QModelIndex &index);
    void on_confirm_clicked();

private:
    Ui::MainWindow *ui;
};
#endif // MAINWINDOW_H

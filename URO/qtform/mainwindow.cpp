#include "mainwindow.h"
#include "ui_mainwindow.h"

#include "stdio.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    struct Package {
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
    };

    ui->pixmap->setPixmap(QPixmap("/Users/Splat/Desktop/MMMuni/URO/Qt_proj/QtForm/cp.png"));
    ui->pixmap_2->setPixmap(QPixmap("/Users/Splat/Desktop/MMMuni/URO/Qt_proj/QtForm/cp.png"));

    std::vector<PackageInternational> data = {};

    struct Countries {
        std::string country;
        std::string tax;
        std::string customs;
        std::string minimumWaitingPeriod;
    };

    std::vector<Countries> lookupModal={
            {"USA", "nad 1500 = 15%", "fixní 5%", "2 měsíce"},
            {"UK", "nad 5000 = 10%", "fixní 10%", "2 týdny"},
            {"RU", "fixní 40%", "fixní 200%", "1 den"},
            {"EU", "nad 5000 = 20%", "bez cla", "2 měsíce"},
            {"UAE", "osvobozeno od DPH", "bez cla", "3 týdny"},
            {"Antarktida", "osvobozeno od DPH", "bez cla", "podle počasí"},
            {"AUS", "nad 20 = 5%", "nad 500 = 20%", "2 měsíce"},
            {"ČR", "fixní 35%", "bez cla", "6 měsíců"},
            {"Japonsko", "osvobozeno od DPH", "fixní 25%", "2 hodiny"},
            {"Mexiko", "osvobozeno od DPH", "bez cla", "dle vytíženosti pilota"}
    };

    QStandardItemModel *model = new QStandardItemModel( data.size(), 9 );

    model->setHorizontalHeaderItem( 0, new QStandardItem( "Hmotnost"));
    model->setHorizontalHeaderItem( 1, new QStandardItem( "Cena"));
    model->setHorizontalHeaderItem( 2, new QStandardItem( "Město"));
    model->setHorizontalHeaderItem( 3, new QStandardItem( "Ulice"));
    model->setHorizontalHeaderItem( 4, new QStandardItem( "PSČ"));
    model->setHorizontalHeaderItem( 5, new QStandardItem( "Jméno"));
    model->setHorizontalHeaderItem( 6, new QStandardItem( "Příjmení"));
    model->setHorizontalHeaderItem( 7, new QStandardItem( "E-mail"));
    model->setHorizontalHeaderItem( 8, new QStandardItem( "Telefon"));

    update();

    QStandardItemModel *countriesModel = new QStandardItemModel( lookupModal.size(), 4 );
    countriesModel->setHorizontalHeaderItem( 0, new QStandardItem( "Země"));
    countriesModel->setHorizontalHeaderItem( 1, new QStandardItem( "Daň"));
    countriesModel->setHorizontalHeaderItem( 2, new QStandardItem( "Clo"));
    countriesModel->setHorizontalHeaderItem( 3, new QStandardItem( "Čekací doba na balíček"));

    for (int r=0; r<lookupModal.size(); r++) {
        QStandardItem *abbr = new QStandardItem(QString(lookupModal[r].country.c_str()));
        ui->cccombo->addItem(QString(lookupModal[r].country.c_str()));
        QStandardItem *tax = new QStandardItem(QString(lookupModal[r].tax.c_str()));
        QStandardItem *customs = new QStandardItem(QString(lookupModal[r].customs.c_str()));
        QStandardItem *waitingTime = new QStandardItem(QString(lookupModal[r].minimumWaitingPeriod.c_str()));
        countriesModel->setItem(r, 0, abbr);
        countriesModel->setItem(r, 1, tax);
        countriesModel->setItem(r, 2, customs);
        countriesModel->setItem(r, 3, waitingTime);
    }

    ui->countries_1->setModel(countriesModel);
    ui->countries_2->setModel(countriesModel);
    ui->packages->setModel(model);
    ui->countries_1->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);
    ui->countries_2->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);
    ui->packages->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);
    ui->countries_1->setEditTriggers(QAbstractItemView::NoEditTriggers);
    ui->countries_2->setEditTriggers(QAbstractItemView::NoEditTriggers);
    ui->packages->setEditTriggers(QAbstractItemView::NoEditTriggers);

    connect(ui->packages, SIGNAL(clicked(const QModelIndex &)), this, SLOT(on_tableView_clicked(const QModelIndex &)));
    connect(ui->confirmBtn, SIGNAL(clicked()), this, SLOT(on_confirm_clicked()));

}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_tableView_clicked(const QModelIndex &index)
{
    //std::cout << "d";
}

void MainWindow::on_confirm_clicked()
{
    qInfo() << this->ui->line1->text();

    PackageInternational package = {
        this->ui->line1->text().toStdString(),
        this->ui->line2->text().toStdString(),
        this->ui->line3->text().toStdString(),
        this->ui->line4->text().toStdString(),
        this->ui->line5->text().toInt(),
        this->ui->line6->text().toInt(),
        this->ui->line7->text().toStdString(),

        this->ui->line8->text().toStdString(),
        this->ui->line9->text().toStdString(),
        this->ui->line10->text().toStdString(),
        this->ui->line11->text().toStdString(),
        this->ui->line12->text().toInt(),
        this->ui->line13->text().toInt(),
        this->ui->line14->text().toStdString(),

        this->ui->line15->text().toInt(),
        this->ui->line16->text().toDouble(),
        this->ui->line17->isChecked(),
        this->ui->cccombo->currentText().toStdString(),
        this->ui->line18->isChecked(),
        this->ui->line19->isChecked()
    };

    this->data.push_back(package);
    update();
}

void MainWindow::update()
{
    /*for (int r=0; r<this->data.size(); r++) {
        QStandardItem *weight = new QStandardItem(QString(std::to_string(this->data[r].weight).c_str()));
        QStandardItem *price = new QStandardItem(QString(std::to_string(this->data[r].price).c_str()));
        QStandardItem *city = new QStandardItem(QString(this->data[r].city2.c_str()));
        QStandardItem *street = new QStandardItem(QString(this->data[r].street2.c_str()));
        QStandardItem *postal = new QStandardItem(QString(std::to_string(this->data[r].postalCode2).c_str()));
        QStandardItem *fname = new QStandardItem(QString(this->data[r].firstName2.c_str()));
        QStandardItem *lname = new QStandardItem(QString(this->data[r].lastName2.c_str()));
        QStandardItem *email = new QStandardItem(QString(this->data[r].email2.c_str()));
        QStandardItem *phone = new QStandardItem(QString(std::to_string(this->data[r].phone2).c_str()));
        this->model->setItem(r, 0, weight);
        this->model->setItem(r, 1, price);
        this->model->setItem(r, 2, city);
        this->model->setItem(r, 3, street);
        this->model->setItem(r, 4, postal);
        this->model->setItem(r, 5, fname);
        this->model->setItem(r, 6, lname);
        this->model->setItem(r, 7, email);
        this->model->setItem(r, 8, phone);
    }*/
}


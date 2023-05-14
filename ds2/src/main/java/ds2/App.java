package ds2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ds2.User.Type;
import ds2_data_layer.ReservationDao;
import ds2_data_layer.UserDao;
import ds2_data_layer.VehicleDao;

public class App {
	
	/* ***********************************************************************************
	 * 
	 * Ve třídě ConnectionProvider je statická metoda pro připojení na přidělenou studentskou Oracle databázi
	 * 
	*********************************************************************************** */

	public static void main(String[] args) {
		
		/* ***********************************************************************************
		 * 
		 * Vytvoření uživatele, vložení do databáze a test seznamu uživatelů / detail uživatele
		 * 
		*********************************************************************************** */
		
		List<User> users = UserDao.getUsers();
		int testId = users.size()+1; // provizorní získání ID nově vytvořeného uživatele
		String vin1 = "SHHEP1" + testId; // unikátní "VIN" pro každé spuštění
		String vin2 = "SHHEP2" + testId;
		
		User user = new User.UserBuilder("test", "testJmeno", "testPrijmeni",
				"test@email.cz", Type.client, true).build();
		UserDao.insertUser(user);
		
		user = UserDao.getUserById(testId);		
		System.out.println("1.1 Nový uživatel, 1.3 Detail uživatele: " + user.getFirstName());
		System.out.println("typ uživatele: " + user.getType());
		
		users = UserDao.getUsers();
		
		for (User u : users) {
			System.out.println("1.2 Seznam uživatelů, 1.3 Detail uživatele: " + u.getFirstName());
		}
		
		/* ***********************************************************************************
		 * 
		 * Aktualizace uživatele - získání uživatele přes ID a aktualizace jeho e-mailu
		 * 
		*********************************************************************************** */
		
		user = UserDao.getUserById(testId);
		user.setEmail("akt@mail.cz");
		System.out.println("E-mail uživatele před změnou: " + UserDao.getUserById(testId).getEmail());
		UserDao.updateUser(user);		
		System.out.println("1.4 Aktualizace uživatele: " + UserDao.getUserById(testId).getEmail());
		
		/* ***********************************************************************************
		 * 
		 * Nastavení uživatele jako partnera
		 * 
		*********************************************************************************** */
		
		LocalDate date = LocalDate.now();
		System.out.println("Typ uživatele před změnou: " + UserDao.getUserById(testId).getType());
		UserDao.setPartnerDate(testId, date);
		System.out.println("1.6 Nastavení partnerského uživatele, nový typ: " 
							+ UserDao.getUserById(testId).getType());
		
		/* ***********************************************************************************
		 * 
		 * Smazání uživatele - nastavení parametru "active" na false - volání uložené procedury, transakce
		 * 
		*********************************************************************************** */
		
		System.out.println("Parametr active před změnou: " + UserDao.getUserById(testId).getActive());
		UserDao.deleteUser(user);
		System.out.println("Transakce, uložená procedura - 1.5 Smazání uživatele, parametr active: "
							+ UserDao.getUserById(testId).getActive());
		
		
		
		User user2 = new User.UserBuilder("jan32", "Jan", "Jánský",
								"email@email.cz", Type.client, true).build();
		
		UserDao.insertUser(user2);
		user2 = UserDao.getUserById(testId + 1);
		
		/* ***********************************************************************************
		 * 
		 * Vytvoření nového záznamu s vozidlem
		 * 
		*********************************************************************************** */
		
		Vehicle vehicle = new Vehicle
				.VehicleBuilder(vin1, user2.getId(), "8T28436", "Honda", "Civic", 2003, true)
				.build();
		
		VehicleDao.insertVehicle(vehicle);
		System.out.println("2.1 Nové vozidlo: " + VehicleDao.getVehicleByVin(vin1).getVIN());		
		System.out.println("2.3 Detail vozidla - valid: " + VehicleDao.getVehicleByVin(vin1).getValid());
		
		/* ***********************************************************************************
		 * 
		 * Nastavení parametru vozidla "valid" na false
		 * 
		*********************************************************************************** */
		
		VehicleDao.setInvalid(vehicle);
		System.out.println("2.4 Aktualizace/smazání vozidla - valid: "
							+ VehicleDao.getVehicleByVin(vin1).getValid());
		
		/* ***********************************************************************************
		 * 
		 * Vytvoření nového vozidla, vytvoření nové rezervace a její získání z databáze
		 * 
		*********************************************************************************** */
		
		vehicle = new Vehicle
				.VehicleBuilder(vin2, user2.getId(), "8T28436", "Honda", "Civic", 2003, true)
				.build();
		
		VehicleDao.insertVehicle(vehicle);
		
		int resId = ReservationDao.getReservations().size()+1; // provizorní získání ID nové rezervace
				
		// pro možnost opakovaného spuštění / testování jsem vytvořil termín s kapacitou 1000
		Reservation res = new Reservation.ReservationBuilder(resId, user2.getId(), 
				LocalDateTime.of(2023, 3, 3, 11, 30), vehicle, "Some car related task", false) 
				.taskDescription("Description of said task")
				.build();
				
		ReservationDao.insertReservation(res);
		
		res = ReservationDao.getReservationById(resId);
		System.out.println("Transakce, uložená procedura (3.1) - 3.1, 3.4 Nová rezervace / Detail - task: "
							+ res.getTask());
		
	}

}

package domain_layer;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginValidation {
	static Logger log = LogManager.getLogger(LoginValidation.class);
	
    public LoginValidation(){}
    
    // password hash
    private static String encrypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        }

        catch (NoSuchAlgorithmException e) {
        	RuntimeException ex = new RuntimeException(e);
        	log.error("Password hash algorithm error", ex);
            throw ex;
        }
    }
    
    // login -> identity field
    public boolean checkCredentials(String loginInput, String passwordInput) {
    	CompletableFuture<List<String>> getData = CompletableFuture.supplyAsync(() -> {
    		List<String> result = new ArrayList<String>();
    		   Path p = Paths.get("./src/main/resources/users.txt");
			
			try (InputStream in = Files.newInputStream(p);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			    String line = null;
			    while ((line = reader.readLine()) != null) {
			        result.add(line);
			    }
			    in.close();
			} catch (IOException x) {
				log.error("login inputstream error", x);
			}
    		   return result;
    		});
    	
    	CompletableFuture<Boolean> future = getData.thenApply(id -> {
    	        for (String element : id) {
    	            int space = element.indexOf(" ");
    	            int divider = element.indexOf("|");
    	            int ID = Integer.parseInt(element.substring(0, divider));
    	            String login = element.substring(divider+1, space);
    	            String password = element.substring(space+1, element.length());
    	            
    	            if (login.equals(loginInput) && password.equals(encrypt(passwordInput))) {
    	                ClientHolder holder = ClientHolder.getInstance();
    	                holder.setClient(new Client(ID, login, null, null, null, 0));
    	                holder.setClient(holder.getClient().getClientDB().load(holder.getClient()));
    	                return true;
    	            }
    	        }
    	        
    	        log.log(Level.INFO, "Invalid login attempt: " + loginInput);
    	        return false;
    	  });
    	
        try {
			return future.get();
		} catch (InterruptedException e) {
			log.log(Level.ERROR, e);
			return false;
		} catch (ExecutionException e) {
			log.log(Level.ERROR, e);
			return false;
		}
    }
    
    // login -> identity field
    public int checkDuplicateLogin(String loginInput) {
    	CompletableFuture<List<String>> getData = CompletableFuture.supplyAsync(() -> {
    		List<String> result = new ArrayList<String>();
    		   Path p = Paths.get("./src/main/resources/users.txt");
			
			try (InputStream in = Files.newInputStream(p);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			    String line = null;
			    while ((line = reader.readLine()) != null) {
			        result.add(line);
			    }
			    in.close();
			} catch (IOException x) {
				log.error("login inputstream error", x);
			}
    		   return result;
    		});

    		CompletableFuture<Integer> checkDuplicateLogin = getData.thenApply(id -> {
    			int ID = 0;
    	        for (String element : id) {
    	            int space = element.indexOf(" ");
    	            int divider = element.indexOf("|");
    	            
    	            String existinglogin = element.substring(divider+1, space);
    	            
    	            if (existinglogin.equals(loginInput)) {
    	                ID = 0;
    	                return -1;
    	            }
    	            ID += 1;
    	        }
    	        
    	        return ID;
    		});
    		
    	try {
			return checkDuplicateLogin.get();
		} catch (InterruptedException e) {
			log.log(Level.ERROR, e);
			return -1;
		} catch (ExecutionException e) {
			log.log(Level.ERROR, e);
			return -1;
		}
    }
    
    public void storeUser(int ID, String login, String password) {
        String p = "./src/main/resources/users.txt";
        try {
            FileWriter out = new FileWriter(p, true);
            out.write("\n" + Integer.toString(ID) + "|" + login + " " + encrypt(password));
            out.close();           
        } catch (IOException x) {
        	log.error("registration error", x);
        }
    }
    
    // login -> identity field for resetting the password
    public boolean tryPasswordReset(String loginInput, String newpassword) {
    	CompletableFuture<Boolean> tryPasswordReset = CompletableFuture.supplyAsync(() -> {
	        int ID = -1;
	        int saveID = -1;
	        String login = "";
	        boolean success = false;
	        Path p = Paths.get("./src/main/resources/users.txt");
	        List<String> result = new ArrayList<String>();
	        
	        try (InputStream in = Files.newInputStream(p);
	            BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                int space = line.indexOf(" ");
	                int divider = line.indexOf("|");
	                ID = Integer.parseInt(line.substring(0, divider));
	                login = line.substring(divider+1, space);                
	                if (login.equals(loginInput)) {
	                    saveID = ID;
	                    success = true;
	                } else result.add(line);
	            }
	            in.close();
	        } catch (IOException x) {
	        	log.error("password reset inputstream error", x);
	        }
	        
	        if (!success) {
	            return false;
	        }
	
	        String write = "./src/main/resources/users.txt";
	        
	        try {
	            FileWriter out = new FileWriter(write);
	            out.write("");
	            out.close(); 
	
	            out = new FileWriter(write, true);
	            for (String element : result) {
	                out.write(element + "\n");
	            }
	            out.write(Integer.toString(saveID) + "|" + loginInput + " " + encrypt(newpassword)); 
	            out.close();           
	        } catch (IOException x) {
	        	log.error("password reset error", x);
	        }
	        
	        return true;
    	});
    	
    	try {
			return tryPasswordReset.get();
		} catch (InterruptedException e) {
			log.log(Level.ERROR, e);
			return false;
		} catch (ExecutionException e) {
			log.log(Level.ERROR, e);
			return false;
		}
    }
}

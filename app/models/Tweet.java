/**
 * 
 */
package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

/**
 * @author chamerling
 *
 */
@Entity
public class Tweet extends Model{
	
	public String status;
	
	public String user;

	public String screenname;

	public String date;
	
	public String iconURL;
	

}

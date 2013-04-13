package user

class UserId {
	
	String login
	String password

    static constraints = {
		login size: 4..15, blank: false, unique: true
		password size: 4..15, blank: false
    }


	/*String toString() {
		"UserId [login=" + login + ", password=" + password + "]"
	}	
	*/
	
}
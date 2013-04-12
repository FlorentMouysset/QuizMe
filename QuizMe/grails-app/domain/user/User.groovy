package user

class User {
	
	String nom
	String prenom
	String sexe
	Date dateDeNaissance
	String email
	//UserId identifiants

    static constraints = {
		prenom(blank:false)
		nom(blank:false)
		sexe(inList:["M","F"])
		email(blank:false, email: true, unique:true)
		dateDeNaissance(validator: {return (it.before(new Date()))})
    }
}

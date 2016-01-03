package net.nikodem.nikodemocracy.test

/**
 * @author Peter Nikodem 
 */
class Persons {
    final String username;
    final String email;
    final String password;
    final String voterKey;

    Persons(username,email,password, voterKey){
        this.username = username
        this.email = email
        this.password = password
        this.voterKey = voterKey
    }


    public static Persons Alice = new Persons('Alice','alice.nikodemocracy@gmail.com','DownTheRabbitHole',"1")
    public static Persons Bob = new Persons('Bob','bob.nikodemocracy@gmail.com','UncleBeans',"2")
    public static Persons Cecil = new Persons('Cecil','cecil.nikodemocracy@gmail.com','ILikeBigButts',"3")
    public static Persons Eva = new Persons("Eva",'eva.evilcorp@gmail.com','ICannotLie',"4")

    public static List<Persons> GoodGuys = [Alice,Bob,Cecil]
    public static List<Persons> Everyone = [Alice,Bob,Cecil,Eva]

}

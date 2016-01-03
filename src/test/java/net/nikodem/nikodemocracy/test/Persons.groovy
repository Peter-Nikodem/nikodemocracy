package net.nikodem.nikodemocracy.test

/**
 * @author Peter Nikodem 
 */
class Persons {
    final String username;
    final String email;
    final String password;

    Persons(username,email,password){
        this.username = username
        this.email = email
        this.password = password
    }


    public static Persons Alice = new Persons('Alice','alice.nikodemocracy@gmail.com','DownTheRabbitHole')
    public static Persons Bob = new Persons('Bob','bob.nikodemocracy@gmail.com','UncleBeans')
    public static Persons Cecil = new Persons('Cecil','cecil.nikodemocracy@gmail.com','ILikeBigButts')
    public static Persons Eva = new Persons("Eva",'eva.evilcorp@gmail.com','ICannotLie')

    public static List<Persons> GoodGuys = [Alice,Bob,Cecil]
    public static List<Persons> Everyone = [Alice,Bob,Cecil,Eva]

}

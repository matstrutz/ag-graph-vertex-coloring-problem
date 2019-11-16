package main;

public class CoisasBobas {

    public static String inteiroFormatado(int value){
        if(value >= 0 && value < 10){
            return String.format("00" + value);
        }
        if(value >= 10 && value < 100){
            return String.format("0" + value);
        }
        return String.format("" + value);
    }
    
	public static int arredondarParaCima(int value) {

		if (value % 2 == 0) {
			return value;
		}
		
		return value + 1;
	}
}

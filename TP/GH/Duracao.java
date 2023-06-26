import java.time.*;

class Duracao{
	Data dataEntrada, dataSaida;
	Horario horarioEntrada , horarioSaida;
	  
	private Duracao(){}	
	public Duracao(String dataEntrada, String horarioEntrada, String dataSaida, String horarioSaida){
		this.dataEntrada = new Data(computeTime(dataEntrada)[0], computeTime(dataEntrada)[1]);
		this.horarioEntrada = new Horario(computeHour(horarioEntrada)[0], computeHour(horarioEntrada)[1]);
		
		this.dataSaida = new Data(computeTime(dataSaida)[0], computeTime(dataSaida)[1]);
		this.horarioSaida = new Horario(computeHour(horarioSaida)[0], computeHour(horarioSaida)[1]);
	}
		
	int[] computeTime(String date){ // metodo para reduzir cada parcela da data a um inteiro
		int[] data = new int[2];
		int i=0;
		for(String str : date.split("/"))
			data[i++]=Integer.parseInt(str);
		return data;
	}
	
	int[] computeHour(String horario){ // transforma a string horario em um vetor de int com as horas e os minutos
		int[] horas = new int[2];
		int i=0;
		for(String buffer : horario.split(":"))
			horas[i++] = Integer.parseInt(buffer);
		return horas;
	}

	public int calcularDiferencaTempo() {
		int diaEntrada = dataEntrada.getDia(), mesEntrada = dataEntrada.getMes(), horaEntrada = horarioEntrada.getHora(), minutoEntrada = horarioEntrada.getMinuto();
		int diaSaida = dataSaida.getDia(), mesSaida = dataSaida.getMes(), horaSaida = horarioSaida.getHora(), minutoSaida = horarioSaida.getMinuto();

		// System.out.println(mesEntrada);
		// System.out.println(mesSaida);

		// Criação dos objetos LocalDateTime para a entrada e saída    	
		LocalDateTime entrada = LocalDateTime.of(2023, mesEntrada, diaEntrada, horaEntrada, minutoEntrada, 0);
		LocalDateTime saida = LocalDateTime.of(2023, mesSaida, diaSaida, horaSaida, minutoSaida, 0);
		
		// Calcula a diferença entre as datas
		Duration duracao = Duration.between(entrada, saida);
		int tempo=(int)(duracao.toHours()*60+duracao.toMinutes()%60);
		return tempo;
	}
}

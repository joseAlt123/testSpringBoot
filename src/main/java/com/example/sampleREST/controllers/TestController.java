package com.example.sampleREST.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sampleREST.dto.Cliente;
import com.example.sampleREST.dto.Promedio;

@RestController
public class TestController {

	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
	public Cliente getById(@PathVariable String id) {
		System.out.println("Se recibe ID:" + id);
		Cliente result = null;
		Cliente item = null;
		ArrayList list = getListClientes();

		for (int i = 0; i < list.size(); i++) {
			item = ((Cliente) list.get(i));
			if (item.getId().longValue() == Long.valueOf(id)) {
				result = item;
				break;
			}
		}

		return result;
	}

	@RequestMapping(value = "/cliente/add", method = RequestMethod.POST, produces = "application/json")		
	public ArrayList addCliente(Cliente cliente) {
		System.out.println("Metodo::: ADD:::");
		
		System.out.println("Id:"+cliente.getId());
		System.out.println("Nombre:"+cliente.getNombre());
		System.out.println("Apellido:"+cliente.getApellido());
		System.out.println("Edad:"+cliente.getEdad());
		System.out.println("FechaNacimiento:"+cliente.getFechaNacimiento());
		
		ArrayList list = getClientes();
		list.add(cliente);
		
		return list;
	}

	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	public ArrayList getClientes() {
		return getListClientes();
	}

	@RequestMapping(value = "/clientes/promedio", method = RequestMethod.GET)
	public Promedio getPromedios() {
		ArrayList list = getListClientes();
		Promedio promedio = new Promedio();

		int[] arreglo = obtenerArreglo(list);
		double valorPromedio = obtenerPromedio(arreglo);
		double valorDesviacion = obtenerDesviacion(arreglo);

		promedio.setPromedio(valorPromedio);
		promedio.setDesviacion(valorDesviacion);

		return promedio;
	}

	private static int[] obtenerArreglo(ArrayList list) {
		int[] arreglo = new int[list.size()];

		for (int i = 0; i < list.size(); i++) {
			arreglo[i] = Integer.valueOf(((Cliente) list.get(i)).getEdad());
		}
		return arreglo;
	}

	public static double obtenerPromedio(int[] v) {
		double prom = 0.0;
		for (int i = 0; i < v.length; i++)
			prom += v[i];

		return prom / (double) v.length;
	}

	public static double obtenerDesviacion(int[] v) {
		double prom, sum = 0;
		int i, n = v.length;
		prom = obtenerPromedio(v);

		for (i = 0; i < n; i++)
			sum += Math.pow(v[i] - prom, 2);

		return Math.sqrt(sum / (double) n);
	}

	private ArrayList getListClientes() {
		ArrayList list = new ArrayList();
		Cliente result = new Cliente();
		result.setId(1L);
		result.setNombre("Luis");
		result.setApellido("Lopez");
		result.setEdad(45);
		result.setFechaNacimiento("01/05/1974");
		list.add(result);

		result = new Cliente();
		result.setId(2L);
		result.setNombre("Juan");
		result.setApellido("Perez");
		result.setEdad(58);
		result.setFechaNacimiento("01/05/1947");
		list.add(result);

		result = new Cliente();
		result.setId(3L);
		result.setNombre("Alberto");
		result.setApellido("Rodriguez");
		result.setEdad(68);
		result.setFechaNacimiento("01/05/1985");
		list.add(result);

		return list;
	}

}

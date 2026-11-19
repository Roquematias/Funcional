import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public record Alumno(String nombre, double nota, String curso) {}

    public static void main(String[] args) {
        List<Alumno> alumnos = List.of(
                new Alumno("Lucía", 8.5, "3A"),
                new Alumno("Mateo", 6.0, "3A"),
                new Alumno("Sofía", 9.2, "3B"),
                new Alumno("Tomás", 7.0, "3A"),
                new Alumno("Valentina", 9.5, "3B"),
                new Alumno("Julián", 5.5, "3C"),
                new Alumno("Camila", 8.8, "3C"),
                new Alumno("Franco", 9.5, "3A")
        );

        List<String> aprobadosOrdenados = alumnos.stream()
                .filter(a -> a.nota() >= 7.0)
                .map(a -> a.nombre().toUpperCase())
                .sorted()
                .toList();

        double promedioGeneral = alumnos.stream()
                .mapToDouble(Alumno::nota)
                .average()
                .orElse(0.0);

        Map<String, List<Alumno>> porCurso = alumnos.stream()
                .collect(Collectors.groupingBy(Alumno::curso));

        List<Alumno> top3AlumnosPorNota = alumnos.stream()
                .sorted(Comparator.comparingDouble(Alumno::nota).reversed()
                        .thenComparing(Alumno::nombre))
                .limit(3)
                .toList();

        Map<String, Double> top3PromedioPorCurso = alumnos.stream()
                .collect(Collectors.groupingBy(
                        Alumno::curso,
                        Collectors.averagingDouble(Alumno::nota)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        System.out.println("1. Aprobados: " + aprobadosOrdenados);
        System.out.println("2. Promedio general: " + promedioGeneral);
        System.out.println("3. Agrupados por curso: " + porCurso);
        System.out.println("4a. Top 3 por nota: " + top3AlumnosPorNota);
        System.out.println("4b. Top 3 promedio por curso: " + top3PromedioPorCurso);
    }
}
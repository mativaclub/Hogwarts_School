package ru.hogwarts.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class HogwartsSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(HogwartsSchoolApplication.class, args);
	}




//**Шаг 6**
//Добавить swagger к проекту. Для этого добавить зависимость к проекту.
//перейти на страницу Swagger-ui в браузере и проверить, что реализация первых трех шагов работает, как
// ожидается, путем вызовов запросов через открывшийся интерфейс.


}

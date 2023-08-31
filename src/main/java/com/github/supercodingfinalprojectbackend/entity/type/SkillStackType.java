package com.github.supercodingfinalprojectbackend.entity.type;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SkillStackType {
	SPRING("Spring", "01"),
	PYTHON("Python", "02"),
	KOTLIN("Kotlin", "03"),
	NETWORK("Network", "04"),
	C_PLUS_PLUS("C++", "05"),
	AWS("AWS", "06"),
	MVC("MVC", "07"),
	SPRING_BOOT("Spring Boot", "08"),
	JPA("JPA", "09"),
	OOP("객체지향", "10"),
	SPRING_JDBC("Spring-jdbc", "11"),
	NODE_JS("Node.js", "12"),
	FLUTTER("Flutter", "13"),
	C_SHOP("C#", "14"),
	ANDROID("Android", "15"),
	SQL("SQL", "16"),
	DBMS_RDBMS("DBMS/RDBMS", "17"),
	MYSQL("MYSQL", "18"),
	MONGODB("MongoDB", "19"),
	PHP("PHP", "20"),
	EXPRESS("Express", "21"),
	JAVA_SCRIPT("JavaScript", "22"),
	VUE_JS("Vue.js", "23"),
	GIT("Git", "24"),
	SOCKET_IO("Socket.io", "25"),
	HTML_CSS("HTML/CSS", "26"),
	REACT("React", "27"),
	JQUERY("jQuery", "28"),
	DOCKER("Docker", "29"),
	GRAPH_QL("GraphQL", "30"),
	DJANGO("Django", "31"),
	NEXT_JS("Next.js", "32"),
	TYPE_SCRIPT("TypeScript", "33"),
	FIREBASE("FireBase", "34"),
	GSAP("gsap", "35"),
	REDUX("Redux", "36"),
	WEBPACK("Webpack", "37"),
	SASS("Sass", "38"),
	KAFKA("Kafka", "39"),
	REDIS("Redis", "40"),
	LINUX("Linux", "41"),
	BOOTSTRAP("Bootstrap", "42"),
	IOS("iOS", "43"),
	SWIFT("Swift", "44"),
	REACT_NATIVE("React Native", "45"),
	SWIFT_UI("SwiftUI", "46"),
	DART("dart", "47"),
	MVVM("MVVM", "48"),
	DOT_NET(".NET", "49"),
	APOLLO("Apollo", "50"),
	POSTGRE_SQL("PostgreSQL", "51"),
	ORACLE("Oracle", "52");

	private final String skillStackName;
	private final String skillStackCode;

	// 커스텀 에러 Refactoring 필요
	public static SkillStackType findBySkillStackType (String inputSkillStack){
		return Arrays.stream(SkillStackType.values())
				.filter(skillStackType -> inputSkillStack.equals(skillStackType.skillStackName))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("존재하지 않는 기술스택입니다."));
	}
}

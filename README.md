# Spark에서 resource 파일 읽기 Test

## spark에서 사용하는 classLoader

```
classloader Name ===> org.apache.spark.util.MutableURLClassLoader@63648ee9
classloader Name ===> sun.misc.Launcher$AppClassLoader@5c647e05
classloader Name ===> sun.misc.Launcher$ExtClassLoader@5733f295
```

## spark-submit으로 테스트한 결과

```
read file by ResourceAsStream : config.txt
getClass file read error : java.lang.NullPointerException


read file by ResourceAsStream : /config.txt
file line : 1,a
file line : 2,b
after read file


read file by Resource : config.txt
getClass file read error : java.lang.NullPointerException


read file by Resource : /config.txt
getClass file read error : java.nio.file.FileSystemNotFoundException


read file by ClassloaderResourceAsStream : config.txt
file line : 1,a
file line : 2,b
after read file


read file by ClassloaderResourceAsStream : /config.txt
getClass file read error : java.lang.NullPointerException


read file by ClassloaderResource : config.txt
getClass file read error : java.nio.file.FileSystemNotFoundException


read file by ClassloaderResource : /config.txt
getClass file read error : java.lang.NullPointerException
```

### 결과

- classLoader에서 getResourceAsStream을 이용한 방식 (file이름은 상대경로) => 동작함!
- classLoader에서 getResourceAsStream을 이용한 방식 (file이름은 절대경로)
- classLoader에서 getResource을 이용한 방식 (file이름은 상대경로)
- classLoader에서 getResource을 이용한 방식 (file이름은 절대경로)
- class에서 getResourceAsStream을 이용한 방식 (file이름은 절대경로) => 동작함!
- class에서 getResourceAsStream을 이용한 방식 (file이름은 상대경로)
- class에서 getResource을 이용한 방식 (file이름은 절대경로)
- class에서 getResource을 이용한 방식 (file이름은 상대경로)

classLoader로 읽든 class에서 바로 읽든 getResourceAsStream만 동작하였다. 
이유를 추정해보면 spark에서 사용하는 classLoader는 3가지이다.(scala나 java에서는 2개)
`org.apache.spark.util.MutableURLClassLoader`에서 처리하는 과정이 다르지 scala나 java와는 다르지 않을까라는 생각을 해본다.
(`MutableURLClassLoader`가 private이라 디버깅이 어렵다.)
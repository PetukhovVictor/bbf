class TestClass {
inline 
 operator fun <T> invoke(task: () -> T) = (task)!!
}
val test = TestClass()
val x = test {}
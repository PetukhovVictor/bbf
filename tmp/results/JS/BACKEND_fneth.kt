fun box()  {
    val obj1 = object : Base( name = "",addr = 1) {}
TODO()
if (0x1L == obj1.addr) obj1.addr = 0x1L
}
open class Base(val addr: Long, 
 name: String)
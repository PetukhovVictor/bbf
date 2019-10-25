inline fun tryZap(  fn: String.(String) -> String) =
        fn(
                TODO(),TODO()
        )
fun run1()  = tryZap{
         ""
    }
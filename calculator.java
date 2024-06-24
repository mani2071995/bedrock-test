java
public int divide(int a, int b) {

  if(b == 0) {
    throw new IllegalArgumentException("Cannot divide by 0");
  }

  return a / b;

}
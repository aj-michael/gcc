public class Particle implements TwoVector
{
   private int px, mass;
   public Particle(int px, int mass)
   {
      this.px = px;
      this.mass = mass;
   }
   public int x() { return x; }
   public int t() { return mass; }
   public int magnitude() 
   {
      return Math.sqrt(x*x + mass*mass);
   }
}
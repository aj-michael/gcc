public class Histogram
{
   Histogram(String name)
   {
      m_name = name;
   }
   public String getName()
   {
      return m_name;
   }
   public void fill(double value, double weight)
   {
      // fill bins here
   }
   private String m_name;
}
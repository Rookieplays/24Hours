package ie.ul.o.daysaver;

/**
 * Created by Ali on 27/04/2018.
 */
import java.util.ArrayList;
public class Organizeby
{
    private ArrayList<ArrayList<String>>list;
    private ArrayList<ArrayList<Integer>>intList;
    private ArrayList<ArrayList<Long>>longList;
    private ArrayList<ArrayList<Character>>CharacterList;
    private ArrayList<ArrayList<String>>returnStringList;
    private ArrayList<ArrayList<Integer>>returnIntList;
    private ArrayList<ArrayList<Character>>returnCharacterList;
    public Organizeby(ArrayList<ArrayList<String>> list)
    {
        this.list=list;

    }

    public Organizeby(ArrayList<ArrayList<Integer>> intList,int intOrder)
    {
        this.intList=intList;

    }
    public Organizeby(ArrayList<ArrayList<Long>> longList,long lorder)
    {
        this.longList=longList;

    }
    public Organizeby(ArrayList<ArrayList<Character>> CharacterList,char charOrder)
    {
        this.CharacterList=CharacterList;

    }

    public ArrayList<ArrayList<String>> ascendingOrder(int sortindex)
    {

        System.out.println("\n"+list.size()+"**");
        ArrayList<ArrayList<String>> temp=new ArrayList<ArrayList<String>>();
        for(int i=0;i<list.size();i++)
        {
            System.out.println(i);
            temp.add(new ArrayList<String>());
        }
        for(int i=0;i<temp.size();i++)
        {
            System.out.println("++"+i);
            temp.get(i).add("");
            System.out.println(temp.get(i));

        }

        for(int i=0; i<list.get(0).size();i++)
        {

            for(int j=i+1; j<list.get(0).size(); j++)
            {
                System.out.println(j+")"+list.get(sortindex).get(i).compareToIgnoreCase(list.get(sortindex).get(j))+"?< 0 ("+list.get(sortindex).get(i)+" is being compared to "+ list.get(sortindex).get(j)+")");
                if(list.get(sortindex).get(j).compareToIgnoreCase(list.get(sortindex).get(i)) < 0)
                {

                    //System.out.println(temp);
                    for(int k=0;k<temp.size();k++)
                    {
                        System.out.println("K:"+k);
                        temp.get(k).set(k, list.get(k).get(j));
                    }
                    for(int k=0;k<list.size();k++)
                    {
                        list.get(k).set(j, list.get(k).get(i));
                    }
                    for(int k=0;k<list.size();k++)
                    {
                        list.get(k).set(i, temp.get(k).get(k));
                    }


                }
            }
        }return list;

    }
    public  ArrayList<ArrayList<String>> descOrder(int sortindex)
    {


        ArrayList<ArrayList<String>> temp=new ArrayList<ArrayList<String>>();
        for(int i=0;i<list.size();i++)
        {
            temp.add(new ArrayList<String>());
        }
        for(int i=0;i<temp.size();i++)
        {

            temp.get(i).add("");

        }

        for(int i=0; i<list.get(0).size();i++)
        {

            for(int j=i+1; j<list.get(0).size(); j++)
            {
               // System.out.println(j+")"+list.get(sortindex).get(i).compareToIgnoreCase(list.get(sortindex).get(j))+"?< 0 ("+list.get(sortindex).get(i)+" is being compared to "+ list.get(sortindex).get(j)+")");
                if(list.get(sortindex).get(j).compareToIgnoreCase(list.get(sortindex).get(i)) > 0)
                {

                    //System.out.println(temp);
                    for(int k=0;k<temp.size();k++)
                    {
                        temp.get(k).set(k, list.get(k).get(j));
                    }
                    for(int k=0;k<list.size();k++)
                    {
                        list.get(k).set(j, list.get(k).get(i));
                    }
                    for(int k=0;k<list.size();k++)
                    {
                        list.get(k).set(i, temp.get(k).get(k));
                    }


                }
            }
        }return list;

    }
    public ArrayList<ArrayList<Integer>> i_ascendingOrder()
    {


        ArrayList<ArrayList<Integer>> temp=new ArrayList<ArrayList<Integer>>();
        temp.add(new ArrayList<Integer>());
        temp.add(new ArrayList<Integer>());
        temp.add(new ArrayList<Integer>());

        temp.get(0).add(0);
        temp.get(1).add(0);
        temp.get(2).add(0);
        for(int i=0; i<intList.get(0).size();i++)
        {

            for(int j=i+1; j<intList.get(0).size(); j++)
            {
                //System.out.println(j+")"+intList.get(0).get(i).compareToIgnoreCase(intList.get(0).get(j))+"?< 0 ("+intList.get(0).get(i)+" is being compared to "+ intList.get(0).get(j)+")");
                if(intList.get(0).get(j)<intList.get(0).get(i))
                {

                    //System.out.println(temp);
                    temp.get(0).set(0, intList.get(0).get(j));
                    temp.get(1).set(0,intList.get(1).get(j));
                    temp.get(2).set(0,intList.get(2).get(j));


                    intList.get(0).set(j, intList.get(0).get(i));
                    intList.get(1).set(j,intList.get(1).get(i));
                    intList.get(2).set(j,intList.get(2).get(i));
                    //System.out.println(intList);
                    intList.get(0).set(i, temp.get(0).get(0));//System.out.println("i'm Putting in temp "+ temp.get(0).get(i)+ " @ " +j);
                    intList.get(1).set(i,temp.get(1).get(0));//System.out.println("i'm Putting in temp "+ temp.get(1).get(i)+ " @ " +j);
                    intList.get(2).set(i,temp.get(2).get(0));//System.out.println("i'm Putting in temp "+ temp.get(2).get(i)+ " @ " +j);

                }
            }
        }return intList;

    }

    public  ArrayList<ArrayList<Integer>> i_descOrder()
    {
        ArrayList<ArrayList<Integer>> temp=new ArrayList<ArrayList<Integer>>();
        temp.add(new ArrayList<Integer>());
        temp.add(new ArrayList<Integer>());
        temp.add(new ArrayList<Integer>());

        temp.get(0).add(0);
        temp.get(1).add(0);
        temp.get(2).add(0);
        for(int i=0; i<intList.get(0).size();i++)
        {

            for(int j=i+1; j<intList.get(0).size(); j++)
            {
                //System.out.println(j+")"+intList.get(0).get(i).compareToIgnoreCase(intList.get(0).get(j))+"?> 0 ("+intList.get(0).get(i)+" is being compared to "+ intList.get(0).get(j)+")");
                if(intList.get(0).get(j)>intList.get(0).get(i))
                {

                    //System.out.println(temp);
                    temp.get(0).set(0, intList.get(0).get(j));
                    temp.get(1).set(0,intList.get(1).get(j));
                    temp.get(2).set(0,intList.get(2).get(j));


                    intList.get(0).set(j, intList.get(0).get(i));
                    intList.get(1).set(j,intList.get(1).get(i));
                    intList.get(2).set(j,intList.get(2).get(i));
                    //System.out.println(intList);
                    intList.get(0).set(i, temp.get(0).get(0));//System.out.println("i'm Putting in temp "+ temp.get(0).get(i)+ " @ " +j);
                    intList.get(1).set(i,temp.get(1).get(0));//System.out.println("i'm Putting in temp "+ temp.get(1).get(i)+ " @ " +j);
                    intList.get(2).set(i,temp.get(2).get(0));//System.out.println("i'm Putting in temp "+ temp.get(2).get(i)+ " @ " +j);

                }
            }
        }return intList;
    }

    public ArrayList<ArrayList<Character>> c_ascendingOrder()
    {


        ArrayList<ArrayList<Character>> temp=new ArrayList<ArrayList<Character>>();
        temp.add(new ArrayList<Character>());
        temp.add(new ArrayList<Character>());
        temp.add(new ArrayList<Character>());

        temp.get(0).add(null);
        temp.get(1).add(null);
        temp.get(2).add(null);
        for(int i=0; i<CharacterList.get(0).size();i++)
        {

            for(int j=i+1; j<CharacterList.get(0).size(); j++)
            {
                //System.out.println(j+")"+CharacterList.get(0).get(i)+"?<" =CharacterList.get(0).get(j)+" ("+CharacterList.get(0).get(i)+" is being compared to "+ CharacterList.get(0).get(j)+")");
                if(CharacterList.get(0).get(j)<CharacterList.get(0).get(i))
                {

                    //System.out.println(temp);
                    temp.get(0).set(0, CharacterList.get(0).get(j));
                    temp.get(1).set(0,CharacterList.get(1).get(j));
                    temp.get(2).set(0,CharacterList.get(2).get(j));


                    CharacterList.get(0).set(j, CharacterList.get(0).get(i));
                    CharacterList.get(1).set(j,CharacterList.get(1).get(i));
                    CharacterList.get(2).set(j,CharacterList.get(2).get(i));
                    //System.out.println(CharacterList);
                    CharacterList.get(0).set(i, temp.get(0).get(0));//System.out.println("i'm Putting in temp "+ temp.get(0).get(i)+ " @ " +j);
                    CharacterList.get(1).set(i,temp.get(1).get(0));//System.out.println("i'm Putting in temp "+ temp.get(1).get(i)+ " @ " +j);
                    CharacterList.get(2).set(i,temp.get(2).get(0));//System.out.println("i'm Putting in temp "+ temp.get(2).get(i)+ " @ " +j);

                }
            }
        }return CharacterList;

    }

    public  ArrayList<ArrayList<Character>> c_descOrder()
    {
        ArrayList<ArrayList<Character>> temp=new ArrayList<ArrayList<Character>>();
        temp.add(new ArrayList<Character>());
        temp.add(new ArrayList<Character>());
        temp.add(new ArrayList<Character>());

        temp.get(0).add(null);
        temp.get(1).add(null);
        temp.get(2).add(null);
        for(int i=0; i<CharacterList.get(0).size();i++)
        {

            for(int j=i+1; j<CharacterList.get(0).size(); j++)
            {
                //System.out.println(j+")"+CharacterList.get(0).get(i)+"?>" =CharacterList.get(0).get(j)+" ("+CharacterList.get(0).get(i)+" is being compared to "+ CharacterList.get(0).get(j)+")");
                if(CharacterList.get(0).get(j)>CharacterList.get(0).get(i))
                {

                    //System.out.println(temp);
                    temp.get(0).set(0, CharacterList.get(0).get(j));
                    temp.get(1).set(0,CharacterList.get(1).get(j));
                    temp.get(2).set(0,CharacterList.get(2).get(j));


                    CharacterList.get(0).set(j, CharacterList.get(0).get(i));
                    CharacterList.get(1).set(j,CharacterList.get(1).get(i));
                    CharacterList.get(2).set(j,CharacterList.get(2).get(i));
                    //System.out.println(CharacterList);
                    CharacterList.get(0).set(i, temp.get(0).get(0));//System.out.println("i'm Putting in temp "+ temp.get(0).get(i)+ " @ " +j);
                    CharacterList.get(1).set(i,temp.get(1).get(0));//System.out.println("i'm Putting in temp "+ temp.get(1).get(i)+ " @ " +j);
                    CharacterList.get(2).set(i,temp.get(2).get(0));//System.out.println("i'm Putting in temp "+ temp.get(2).get(i)+ " @ " +j);

                }
            }
        }return CharacterList;
    }
    public ArrayList<ArrayList<Long>> l_ascendingOrder()
    {


        ArrayList<ArrayList<Long>> temp=new ArrayList<ArrayList<Long>>();
        temp.add(new ArrayList<Long>());
        temp.add(new ArrayList<Long>());
        temp.add(new ArrayList<Long>());

        temp.get(0).add(0L);
        temp.get(1).add(0L);
        temp.get(2).add(0L);
        for(int i=0; i<longList.get(0).size();i++)
        {

            for(int j=i+1; j<longList.get(0).size(); j++)
            {
                //System.out.println(j+")"+longList.get(0).get(i).compareToIgnoreCase(longList.get(0).get(j))+"?< 0 ("+longList.get(0).get(i)+" is being compared to "+ longList.get(0).get(j)+")");
                if(longList.get(0).get(j)<longList.get(0).get(i))
                {

                    //System.out.println(temp);
                    temp.get(0).set(0, longList.get(0).get(j));
                    temp.get(1).set(0,longList.get(1).get(j));
                    temp.get(2).set(0,longList.get(2).get(j));


                    longList.get(0).set(j, longList.get(0).get(i));
                    longList.get(1).set(j,longList.get(1).get(i));
                    longList.get(2).set(j,longList.get(2).get(i));
                    //System.out.println(longList);
                    longList.get(0).set(i, temp.get(0).get(0));//System.out.println("i'm Putting in temp "+ temp.get(0).get(i)+ " @ " +j);
                    longList.get(1).set(i,temp.get(1).get(0));//System.out.println("i'm Putting in temp "+ temp.get(1).get(i)+ " @ " +j);
                    longList.get(2).set(i,temp.get(2).get(0));//System.out.println("i'm Putting in temp "+ temp.get(2).get(i)+ " @ " +j);

                }
            }
        }return longList;

    }

    public  ArrayList<ArrayList<Long>> l_descOrder()
    {
        ArrayList<ArrayList<Long>> temp=new ArrayList<ArrayList<Long>>();
        temp.add(new ArrayList<Long>());
        temp.add(new ArrayList<Long>());
        temp.add(new ArrayList<Long>());

        temp.get(0).add(0L);
        temp.get(1).add(0L);
        temp.get(2).add(0L);
        for(int i=0; i<longList.get(0).size();i++)
        {

            for(int j=i+1; j<longList.get(0).size(); j++)
            {
                //System.out.println(j+")"+longList.get(0).get(i).compareToIgnoreCase(longList.get(0).get(j))+"?> 0 ("+longList.get(0).get(i)+" is being compared to "+ longList.get(0).get(j)+")");
                if(longList.get(0).get(j)>longList.get(0).get(i))
                {

                    //System.out.println(temp);
                    temp.get(0).set(0, longList.get(0).get(j));
                    temp.get(1).set(0,longList.get(1).get(j));
                    temp.get(2).set(0,longList.get(2).get(j));


                    longList.get(0).set(j, longList.get(0).get(i));
                    longList.get(1).set(j,longList.get(1).get(i));
                    longList.get(2).set(j,longList.get(2).get(i));
                    //System.out.println(longList);
                    longList.get(0).set(i, temp.get(0).get(0));//System.out.println("i'm Putting in temp "+ temp.get(0).get(i)+ " @ " +j);
                    longList.get(1).set(i,temp.get(1).get(0));//System.out.println("i'm Putting in temp "+ temp.get(1).get(i)+ " @ " +j);
                    longList.get(2).set(i,temp.get(2).get(0));//System.out.println("i'm Putting in temp "+ temp.get(2).get(i)+ " @ " +j);

                }
            }
        }return longList;
    }

}
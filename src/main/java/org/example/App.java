package org.example;

import org.example.util.Configure;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        Configure.getInstance().initProperties();
    }
}

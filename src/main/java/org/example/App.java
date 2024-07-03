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
        Configure.getInstance().initProperties();

        System.out.println( "Hello World!" );
    }
}

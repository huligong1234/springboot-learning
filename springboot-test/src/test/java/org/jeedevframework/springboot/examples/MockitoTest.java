package org.jeedevframework.springboot.examples;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class MockitoTest {

	
	@Test  
	public void argumentMatcherTest(){  
	    List<String> list = mock(List.class);  
	    when(list.get(anyInt())).thenReturn("hello","world");  
	    String result = list.get(0)+list.get(1);  
	    verify(list,times(2)).get(anyInt());  
	    Assert.assertEquals("helloworld", result);  
	}
	
	@Test  
	public void testVerify(){  
	    List<String> mockedList = mock(List.class);  
	    //using mock   
	    mockedList.add("once");  
	      
	    mockedList.add("twice");  
	    mockedList.add("twice");  
	      
	    mockedList.add("three times");  
	    mockedList.add("three times");  
	    mockedList.add("three times");  
	      
	    //following two verifications work exactly the same - times(1) is used by default  
	    verify(mockedList).add("once");  
	    verify(mockedList, times(1)).add("once");  
	      
	    //exact number of invocations verification  
	    verify(mockedList, times(2)).add("twice");  
	    verify(mockedList, times(3)).add("three times");  
	      
	    //verification using never(). never() is an alias to times(0)  
	    verify(mockedList, never()).add("never happened");  
	      
	    //verification using atLeast()/atMost()  
	    verify(mockedList, atLeastOnce()).add("three times");  
	    verify(mockedList, atLeast(2)).add("five times");  
	    verify(mockedList, atMost(5)).add("three times");  
	}
	
	
	//spy的意思是你可以修改某个真实对象的某些方法的行为特征，而不改变他的基本行为特征，这种策略的使用跟AOP有点类似。
	@Test
	public void testSpy() {
	    List list = new LinkedList();  
	    List spy = spy(list);  
	      
	    //optionally, you can stub out some methods:  
	    when(spy.size()).thenReturn(100);  
	       
	    //using the spy calls <b>real</b> methods  
	    spy.add("one");  
	    spy.add("two");  
	       
	    //prints "one" - the first element of a list  
	    System.out.println(spy.get(0));  
	       
	    //size() method was stubbed - 100 is printed  
	    System.out.println(spy.size());  
	       
	    //optionally, you can verify  
	    verify(spy).add("one");  
	    verify(spy).add("two");  
	}
}

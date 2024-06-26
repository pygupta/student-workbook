package com.aerospike;

import com.aerospike.client.exp.Exp;
import com.aerospike.client.exp.Expression;

public class SizeFilter {

  public static void main(String[] args) {
    Expression filter = Exp.build( Exp.ge(Exp.deviceSize(), Exp.val(127 * 1024)) );
    String sFilter = filter.getBase64();
    System.out.println("Filter in base64:"+ sFilter); //Prints: kwSRQc4AAfwA
  }
}

/*
Set filter on any node, will be set on all via SMD.

$ asinfo -v "xdr-set-filter:dc=DC1;namespace=ns1;exp=kwSRQc4AAfwA"'
ok

Check the filter is there:

$ asinfo -v "xdr-get-filter:dc=DC1;namespace=ns1"
namespace=shared:exp=ge(device_size(), 130048)

Delete the filter:
$ asinfo -v "xdr-set-filter:dc=DC1;namespace=ns1;exp=null"'
ok
*/

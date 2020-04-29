package com.doggy.misctest;

import lombok.Data;

public class AmericanCovid19Check {
    private static String US_DATA =
            "Sun April 12 2020\t56\t140226\t551826\t2254066\t2805892\t16419\t21919\t2805892\n" +
            "Sat April 11 2020\t56\t136384\t522843\t2142823\t2665666\t16593\t20355\t2665666\n" +
            "Fri April 10 2020\t56\t153927\t493252\t2036030\t2529282\t17435\t18488\t2529282\n" +
            "Thu April 9 2020\t56\t162789\t458635\t1916720\t2375355\t17622\t16424\t2375355\n" +
            "Wed April 8 2020\t56\t139536\t424289\t1788277\t2212566\t17219\t14547\t2212566\n" +
            "Tue April 7 2020\t56\t148099\t394156\t1678874\t2073030\t16548\t12646\t2073030\n" +
            "Mon April 6 2020\t56\t149248\t363719\t1561212\t1924931\t17283\t10720\t1924931\n" +
            "Sun April 5 2020\t56\t122603\t334967\t1440716\t1775683\t17303\t9554\t1775683\n" +
            "Sat April 4 2020\t56\t229268\t308993\t1344087\t1653080\t15569\t8379\t1653080\n" +
            "Fri April 3 2020\t56\t132011\t275457\t1148355\t1423812\t61976\t7026\t1423812\n" +
            "Thu April 2 2020\t56\t121955\t243403\t1048398\t1291801\t62097\t5835\t1291801\n" +
            "Wed April 1 2020\t56\t103481\t215329\t954517\t1169846\t59665\t4746\t1169846\n" +
            "Tue March 31 2020\t56\t106972\t190078\t876287\t1066365\t59518\t3803\t1066365\n" +
            "Mon March 30 2020\t56\t118644\t165597\t793796\t959393\t65369\t2983\t959393\n" +
            "Sun March 29 2020\t56\t93086\t144377\t696372\t840749\t65545\t2467\t840749\n" +
            "Sat March 28 2020\t56\t107930\t124884\t622779\t747663\t65709\t2001\t747663\n" +
            "Fri March 27 2020\t56\t110641\t105484\t534249\t639733\t60091\t1574\t639733\n" +
            "Thu March 26 2020\t56\t96393\t86811\t442281\t529092\t60251\t1208\t529092\n" +
            "Wed March 25 2020\t56\t83540\t69508\t363191\t432699\t51235\t931\t432699\n" +
            "Tue March 24 2020\t56\t66394\t52574\t296585\t349159\t14433\t706\t349159\n" +
            "Mon March 23 2020\t56\t55331\t42605\t240160\t282765\t14571\t509\t282765\n" +
            "Sun March 22 2020\t56\t46219\t32328\t195106\t227434\t2842\t426\t227434\n" +
            "Sat March 21 2020\t56\t44815\t23679\t157536\t181215\t3468\t297\t181215\n" +
            "Fri March 20 2020\t56\t35383\t17430\t118970\t136400\t3330\t247\t136400\n" +
            "Thu March 19 2020\t56\t25743\t12078\t88939\t101017\t3016\t185\t101017\n" +
            "Wed March 18 2020\t56\t21183\t8106\t67168\t75274\t2526\t142\t75274\n" +
            "Tue March 17 2020\t56\t13685\t6038\t48053\t54091\t1687\t119\t54091\n" +
            "Mon March 16 2020\t56\t14327\t4302\t36104\t40406\t1691\t97\t40406\n" +
            "Sun March 15 2020\t51\t6302\t3455\t22624\t26079\t2242\t76\t26079\n" +
            "Sat March 14 2020\t51\t3998\t2675\t17102\t19777\t1236\t63\t19777\n" +
            "Fri March 13 2020\t51\t6217\t2166\t13613\t15779\t1130\t55\t15779\n" +
            "Thu March 12 2020\t51\t2194\t1521\t8041\t9562\t673\t51\t9562\n" +
            "Wed March 11 2020\t51\t2549\t1262\t6106\t7368\t563\t43\t7368\n" +
            "Tue March 10 2020\t51\t681\t1007\t3812\t4819\t469\t37\t4819\n" +
            "Mon March 9 2020\t51\t1240\t794\t3344\t4138\t313\t35\t4138\n" +
            "Sun March 8 2020\t51\t637\t563\t2335\t2898\t347\t31\t2898\n" +
            "Sat March 7 2020\t51\t377\t422\t1839\t2261\t602\t27\t2261\n" +
            "Fri March 6 2020\t37\t710\t296\t1588\t1884\t458\t26\t1884\n" +
            "Thu March 5 2020\t25\t258\t204\t970\t1174\t197\t20\t1174\n" +
            "Wed March 4 2020\t15\t850\t157\t759\t916\t103\t16\t916";
    public static void main(String[] args) {
        String[] rows = US_DATA.split("\n");
        for (int i = 0; i < rows.length - 1; i++) {
            Row current = new Row(rows[i]);
            Row pre = new Row(rows[i+1]);
            System.out.println(current.date + ":" + (float)(current.totalPositive - pre.totalPositive)/(float)(current.totalTest - pre.totalTest));
        }
    }

    @Data
    private static class Row{
        private String date;
        private int totalPositive;
        private int totalTest;

        public Row(String rowData) {
            String[] ss = rowData.split("\t");
            date = ss[0];
            totalPositive = Integer.valueOf(ss[3]);
            totalTest = Integer.valueOf(ss[8]);
        }
    }
}

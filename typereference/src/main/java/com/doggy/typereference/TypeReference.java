/**
 *    Copyright 2009-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.doggy.typereference;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * References a generic type.
 * X<T> extends TypeReference<T>, 然后使用（new X<String>()）.getRawType实际上不能正常工作!
 * 因为getGenericSuperclass只和发起调用的Class对象有关,调用结果只取决于发起调用的Class类定义有关！
 * 与类对应的实例以及相应的对象都没有关系！！！
 *
 * getGenericSuperclass的返回值！
 * 对于 X extends Y: X.class.getGenericSuperclass() 则返回Class对象（Y.class）
 * 对于 X extends Y<String>:  X.class.getGenericSuperclass() 则返回ParameterizedType对象(pt:{Y<String>}), 且pt.getActualTypeArguments()[0]为Class对象（String.class）
 * 对于 X<T> extends Y<T>(T不是具体类型)：  X.class.getGenericSuperclass() 则返回ParameterizedType对象(pt:{Y<T>}), 且pt.getActualTypeArguments()[0]为TypeVariableImpl对象(T)
 *
 * @param <T> the referenced type
 * @since 3.1.0
 * @author Simone Tripodi
 */
public abstract class TypeReference<T> {

  private final Type rawType;

  protected TypeReference() {
    rawType = getSuperclassTypeParameter(getClass());
  }

  /**
   * 会拿到clazz第一个泛型父类中的泛型的具体类型！ 如 X extends Y, Y extends Z<String>, 则会拿到String
   * @param clazz
   * @return
   */
  Type getSuperclassTypeParameter(Class<?> clazz) {
    // java.lang.Class.getGenericSuperclass
    // 这个方法在子类的直接父类不是一个泛型时，会返回父类的Class类型，如X.class.getGenericSuperclass => Y.class
    // 在子类的直接父类为泛型时，会返回代表相应泛型的ParameterizedType, 如Y.class.getGenericSuperclass => Z<String>(ParameterizedType)
    Type genericSuperclass = clazz.getGenericSuperclass();
    if (genericSuperclass instanceof Class) {
      // try to climb up the hierarchy until meet something useful
      if (TypeReference.class != genericSuperclass) {
        return getSuperclassTypeParameter(clazz.getSuperclass());
      }

      throw new IllegalStateException("'" + getClass() + "' extends TypeReference but misses the type parameter. "
        + "Remove the extension or add a type parameter to it.");
    }

    Type rawType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
    // TODO remove this when Reflector is fixed to return Types
    // 注意！！！ 这里如果发现内部还是一个泛型，则会直接取泛型类！如Z<List<Integer>> => List
    if (rawType instanceof ParameterizedType) {
      rawType = ((ParameterizedType) rawType).getRawType();
    }

    return rawType;
  }

  public final Type getRawType() {
    return rawType;
  }

  @Override
  public String toString() {
    return rawType.toString();
  }

}

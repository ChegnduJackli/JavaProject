
var emptyObject = Object.freeze({});

// These helpers produce better VM code in JS engines due to their
// explicitness and function inlining.
function isUndef (v) {
    return v === undefined || v === null
}

function isDef (v) {
    return v !== undefined && v !== null
}

function isTrue (v) {
    return v === true
}

function isFalse (v) {
    return v === false
}

/**
 * Check if value is primitive.
 */
function isPrimitive (value) {
    return (
        typeof value === 'string' ||
        typeof value === 'number' ||
        // $flow-disable-line
        typeof value === 'symbol' ||
        typeof value === 'boolean'
    )
}

/**
 * Quick object check - this is primarily used to tell
 * Objects from primitive values when we know the value
 * is a JSON-compliant type.
 */
function isObject (obj) {
    return obj !== null && typeof obj === 'object'
}

/**
 * Get the raw type string of a value, e.g., [object Object].
 */
var _toString = Object.prototype.toString;

function toRawType (value) {
    return _toString.call(value).slice(8, -1)
}

/**
 * Strict object type check. Only returns true
 * for plain JavaScript objects.
 */
function isPlainObject (obj) {
    return _toString.call(obj) === '[object Object]'
}

function isRegExp (v) {
    return _toString.call(v) === '[object RegExp]'
}

/**
 * Check if val is a valid array index.
 */
function isValidArrayIndex (val) {
    var n = parseFloat(String(val));
    return n >= 0 && Math.floor(n) === n && isFinite(val)
}

function isPromise (val) {
    return (
        isDef(val) &&
        typeof val.then === 'function' &&
        typeof val.catch === 'function'
    )
}

/**
 * Convert a value to a string that is actually rendered.
 */
function toString (val) {
    return val == null
        ? ''
        : Array.isArray(val) || (isPlainObject(val) && val.toString === _toString)
            ? JSON.stringify(val, null, 2)
            : String(val)
}

/**
 * Convert an input value to a number for persistence.
 * If the conversion fails, return original string.
 */
function toNumber (val) {
    var n = parseFloat(val);
    return isNaN(n) ? val : n
}

/**
 * Make a map and return a function for checking if a key
 * is in that map.
 */
function makeMap (
    str,
    expectsLowerCase
) {
    var map = Object.create(null);
    var list = str.split(',');
    for (var i = 0; i < list.length; i++) {
        map[list[i]] = true;
    }
    return expectsLowerCase
        ? function (val) { return map[val.toLowerCase()]; }
        : function (val) { return map[val]; }
}

/**
 * Check if a tag is a built-in tag.
 */
var isBuiltInTag = makeMap('slot,component', true);

/**
 * Check if an attribute is a reserved attribute.
 */
var isReservedAttribute = makeMap('key,ref,slot,slot-scope,is');

/** 数组里面删除元素
 * Remove an item from an array.
 */
function remove (arr, item) {
    if (arr.length) {
        var index = arr.indexOf(item);
        if (index > -1) {
            return arr.splice(index, 1)
        }
    }
}

/**
 * Check whether an object has the property.
 */
var hasOwnProperty = Object.prototype.hasOwnProperty;
function hasOwn (obj, key) {
    return hasOwnProperty.call(obj, key)
}

/**生成带有缓存的函数（闭包的应用）,提高性能
 * 使用越多的数据，缓存越有用
 * Create a cached version of a pure function.
 */
function cached (fn) {
    var cache = Object.create(null);
    return (function cachedFn (str) {
        var hit = cache[str];
        return hit || (cache[str] = fn(str))
    })
}


/**
 * 等价代码
 * 
 * let hit = cached[ str ];
 * if ( hit === undefined ) {
 *   let res = fn( str );
 *   cached[ str ] = res;
 *   return res;
 * } else {
 *   return hit;
 * }
 */



/** 骆驼命名
 * Camelize a hyphen-delimited string.
 */
var camelizeRE = /-(\w)/g;
var camelize = cached(function (str) {
    return str.replace(camelizeRE, function (_, c) { return c ? c.toUpperCase() : ''; })
});

/** //首字母大写
 * Capitalize a string.
 */
var capitalize = cached(function (str) {
    return str.charAt(0).toUpperCase() + str.slice(1)
});

/** 驼峰改为连字符，如GoodJob => good-job
 * Hyphenate a camelCase string.
 */
var hyphenateRE = /\B([A-Z])/g;
var hyphenate = cached(function (str) {
    return str.replace(hyphenateRE, '-$1').toLowerCase()
});

// vue 运行在浏览器中, 所以需要考虑性能
// 每次数据的更新 -> 虚拟 DOM 的生成( 模板解析的行为 ) -> 因此将经常使用的字符串与算法进行缓存
// 在垃圾回收的原则中有一个统计现象: "使用的越多的数据, 一般都会频繁的使用"
// 1. 每次创建一个数据, 我们就会考虑是否要将其回收
// 2. 在数据达到一定限额的时候, 就会考虑将数据回收 ( 回收不是实时 )
//   - 如果每次都有判断对象是否需要回收, 那么就需要遍历
//   - 将对象进行划分, 统计, 往往一个数据使用完以后就必须要使用了.
//   - 一个对象如果在一次回收之后还保留下来, 统计的结果是这个对象会比较持久在内存中驻留.
// 在模板中常常会使用 "指令", 在 vue 中一个 xxx-xxx-xxx 的形式出现的属性 
// 每次数据的更新都可能会带来 指令的 解析, 所以解析就是字符串处理, 一般会消耗一定的性能.



/** 兼容低版本浏览器
 * Simple bind polyfill for environments that do not support it,
 * e.g., PhantomJS 1.x. Technically, we don't need this anymore
 * since native bind is now performant enough in most browsers.
 * But removing it would mean breaking code that was able to run in
 * PhantomJS 1.x, so this must be kept for backward compatibility.
 */

/* istanbul ignore next */
function polyfillBind (fn, ctx) {
    function boundFn (a) {
        var l = arguments.length;
        return l
            ? l > 1
                ? fn.apply(ctx, arguments)
                : fn.call(ctx, a)
            : fn.call(ctx)
    }

    boundFn._length = fn.length;
    return boundFn
}

function nativeBind (fn, ctx) {
    return fn.bind(ctx)
}

var bind = Function.prototype.bind
    ? nativeBind
    : polyfillBind;

/**
 * Convert an Array-like object to a real Array.
 */
function toArray (list, start) {
    start = start || 0;
    var i = list.length - start;
    var ret = new Array(i);
    while (i--) {
        ret[i] = list[i + start];
    }
    return ret
}

/**
 * Mix properties into target object.
 */
function extend (to, _from) {
    for (var key in _from) {
        to[key] = _from[key];
    }
    return to
}

/**
 * Merge an Array of Objects into a single Object.
 */
function toObject (arr) {
    var res = {};
    for (var i = 0; i < arr.length; i++) {
        if (arr[i]) {
            extend(res, arr[i]);
        }
    }
    return res
}

/* eslint-disable no-unused-vars */

/**
 * Perform no operation.
 * Stubbing args to make Flow happy without leaving useless transpiled code
 * with ...rest (https://flow.org/blog/2017/05/07/Strict-Function-Call-Arity/).
 */
function noop (a, b, c) { }

/**
 * Always return false.
 */
var no = function (a, b, c) { return false; };

/* eslint-enable no-unused-vars */

/**
 * Return the same value.
 */
var identity = function (_) { return _; };

/**
 * Generate a string containing static keys from compiler modules.
 */
function genStaticKeys (modules) {
    return modules.reduce(function (keys, m) {
        return keys.concat(m.staticKeys || [])
    }, []).join(',')
}

// 在面试中可能会遇到, 思想重要
// 比较两个对象是否是相等的 两个对象
// 1. js 中对象是无法使用 == 来比较的, 比是地址
// 2. 我们一般会定义如果对象的各个属性值都相等 那么对象就是相等的对象. 例如: {} 就与 {} 相等.
// 算法描述
// 1. 假定对象 a 和 b
// 2. 遍历 a 中的成员, 判断是否每一个 a 中的成员都在 b 中. 并且 与 b 中的对应成员相等.
// 3. 再遍历 b 中的成员, 判断是否每一个 b 中的成员都在 a 中. 并且 与 a 中的对应成员相等.
// 4. 如果成员是引用类型, 递归.

// 抽象一下, 判断两个集合是否相等

/**
 * Check if two values are loosely equal - that is,
 * if they are plain objects, do they have the same shape?
 */
function looseEqual (a, b) {
    if (a === b) { return true }
    var isObjectA = isObject(a);
    var isObjectB = isObject(b);
    if (isObjectA && isObjectB) {
        try {
            var isArrayA = Array.isArray(a);
            var isArrayB = Array.isArray(b);
            if (isArrayA && isArrayB) {
                return a.length === b.length && a.every(function (e, i) {
                    return looseEqual(e, b[i]) // b 包含 a
                })
            } else if (a instanceof Date && b instanceof Date) {
                return a.getTime() === b.getTime()
            } else if (!isArrayA && !isArrayB) {
                var keysA = Object.keys(a);
                var keysB = Object.keys(b);
                //先判断 key 的长度, 再判断 a 包含于 b
                return keysA.length === keysB.length && keysA.every(function (key) {
                    return looseEqual(a[key], b[key])
                })
            } else {
                /* istanbul ignore next */
                return false
            }
        } catch (e) {
            /* istanbul ignore next */
            return false
        }
    } else if (!isObjectA && !isObjectB) {
        return String(a) === String(b)
    } else {
        return false
    }
}

/**
 * Return the first index at which a loosely equal value can be
 * found in the array (if value is a plain object, the array must
 * contain an object of the same shape), or -1 if it is not present.
 */
function looseIndexOf (arr, val) {
    for (var i = 0; i < arr.length; i++) {
        if (looseEqual(arr[i], val)) { return i }
    }
    return -1
}


// 让一个事件 ( 一个函数 ) 只允许调用一次
// 在 vue 中有函数方法 ( on, off 等, once ), once 事件就是这个思路

/**
 * Ensure a function is called only once.
 */
function once (fn) {
    var called = false;
    return function () {
        if (!called) {
            called = true;
            fn.apply(this, arguments);
        }
    }
}



/**
 * Parse simple path.
 * 解析person.child.name的值
 */
 const bailRE = new RegExp(`[^${unicodeRegExp.source}.$_\\d]`) // 用于 匹配 xxx.xx.xx.x 正则表达式
 export function parsePath (path) {
   if (bailRE.test(path)) {
     return
   }
   const segments = path.split('.')
   return function (obj) {
     for (let i = 0; i < segments.length; i++) {
       if (!obj) return
       obj = obj[segments[i]]
     }
     return obj
   }
 }
 
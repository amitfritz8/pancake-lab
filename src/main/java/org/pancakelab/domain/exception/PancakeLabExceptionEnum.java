package org.pancakelab.domain.exception;


import lombok.Getter;

/**
 * Contains specific project related errors. These are raised by throwing a {@link PancakeLabException}
 * and handled by the global exception handler.
 * <p>
 * All errors will have an error code, which is shown and easy to find in logs. The codes
 * will follow the format:
 * <p>
 * PLE10001
 * <hr>
 * <pre>
 * The numbers represent the type of errors.
 *    PLE  : represents Pancake Lab Error always like this for defined error
 *    1   : main type
 *    0   : sub type
 *    001 : specific error code
 * </pre>
 *
 * <pre>
 * Main error types:
 *    0 : Unknown errors. The full 0 (DE00000) code means that an unhandled error
 *        occurred. This is usually a non checked exception like NullPtr.
 *    1 : Invalid order state. Issue is on order state.
 *       0: default
 *       1: order creation related errors
 *       2: order completion related error
 *       3: order prepare related errors
 *       4: order delivery related errors
 *    2 : Internal errors because of missing information.
 *       0: default
 *       1: data missing related (for example missing address when creating order)
 *
 * </pre>
 */
@Getter
public enum PancakeLabExceptionEnum {

  DEFAULT_UNEXPECTED_ERROR("PLE00000", "Unexpected error occurred!"),

  // Invalid Order State
  PARAM_ILLEGAL("PLE11001", "Illegal parameter"),
  ORDER_CREATION_ERROR("PLE12002", "Order Creation Error"),
  ORDER_COMPLETION_ERROR("PLE12003", "Order Completion Error"),
  ORDER_PREPARATION_ERROR("PLE14004", "Order Preparation Error"),
  ORDER_DELIVERY_ERROR("PLE15005", "Order Delivery Error"),
  PANCAKE_DECORATION_ERROR("PLE15006", "Pancake Decoration Error"),


  //  Missing information
  MISSING_DATA("DE21001", "Data does not exist"),
  INVALID_DATA("DE21002", "Invalid data"),
  ORDER_NOT_FOUND("DE21003", "Order Not Found"),
  ;

  /**
   * Constructor for default enabled stack trace.
   */
  PancakeLabExceptionEnum(String pancakeLabErrorCode, String message) {
    this.pancakeLabErrorCode = pancakeLabErrorCode;
    this.message = message;
  }

  private final String pancakeLabErrorCode;
  private final String message;

}
